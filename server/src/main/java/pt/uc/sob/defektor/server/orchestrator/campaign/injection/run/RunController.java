package pt.uc.sob.defektor.server.orchestrator.campaign.injection.run;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.DataCollectorPlug;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class RunController {

    private final WorkloadGenerator workloadGenerator;
    private final CampaignService campaignService;

    private List<SystemConnectorPlug> systemConnectorPlugs;
    private CampaignData campaignData;
    private IjkData ijk;
    private WorkLoadData workLoad;
    private DataCollectorData dataCollector;
    private RunData runData;
    private InjektorPlug injektorPlug;

    public void configure(CampaignData campaignData, InjektionData injektionData, List<SystemConnectorPlug> systemConnectorPlugs, RunData run) {
        this.campaignData = campaignData;
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.runData = run;
    }

    public void performRun() {
        handleRunStart();

        try {
            performGoldenRun();
            sleep(30);
            performFaultInjectionRun();
            handleRunFinish();
        } catch (CampaignException e) {
            handleAbnormallyInterruptedRun(e);
        }
    }

    private void handleRunStart() {
        runData.setStartTimestamp(Utils.Time.getCurrentTimestamp());
    }

    private void handleRunFinish() {
        runData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateRunState(
                RunStatus.FINISHED,
                Strings.Run.FINISHED
        );
    }

    public void performGoldenRun() throws CampaignException {
        updateRunState(
                RunStatus.RUNNING_GOLDEN_RUN,
                Strings.Run.RUNNING_GOLDEN_RUN
        );
        applyWorkload();
        stopWorkload();
        collectData();
    }

    public void performFaultInjectionRun() throws CampaignException {
        updateRunState(
                RunStatus.RUNNING_FAULT_INJECTION_RUN,
                Strings.Run.RUNNING_FAULT_INJECTION_RUN
        );

        performInjektion();
        applyWorkload();
        stopWorkload();
        stopInjektion();
        collectData();
    }

    private void handleAbnormallyInterruptedRun(CampaignException e) {
        runData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateRunState(
                RunStatus.ABNORMALLY_INTERRUPTED,
                e.getMessage()
        );
    }

    public void performInjektion() throws CampaignException {
//        System.out.println(new Date() + " - PERFORMING INJEKTION");
        log.info(campaignData.getId() + ".Run_" + runData.getRunNumber() + " - performing injektion");
        for(SystemConnectorPlug plug : systemConnectorPlugs) {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(ijk.getName(), plug);
            injektorPlug.performInjection(buildIjkParams(ijk.getParams()));
        }
    }

    public void stopInjektion() throws CampaignException {
        System.out.println(new Date() + " - STOPPING INJEKTION");
        injektorPlug.stopInjection();
    }

    private void applyWorkload() throws CampaignException {
//        System.out.println(new Date() + " - STARTING WORKLOAD");
        log.info("starting workload");
        workloadGenerator.performWorkloadGen(workLoad, campaignData.getId(), runData);
        sleep(workLoad.getDuration());
    }

    private void stopWorkload() throws CampaignException {
        System.out.println(new Date() + " - STOPPING WORKLOAD");
//        workloadGenerator.stopWorkloadGen(campaignData.getId(), runData);

        String faultOccurrence = ijk.getParams().get(4).getValue();
        Integer duration = workLoad.getDuration() / 60;

        workloadGenerator.stopWorkloadGen(
                campaignData.getId(),
                runData,
                faultOccurrence,
                duration
        );
        sleep(30);
    }

    private void collectData() throws CampaignException {
        System.out.println(new Date() + " - COLLECTING DATA");

        DataCollectorPlug dataCollectorPlug = (DataCollectorPlug) DataCollectorPluginFactory
                .getInstance()
                .getPluginInstance(
                        dataCollector.getName(),
                        buildDataCollectorParams(
                                dataCollector.getParams()
                        )
                );

        byte[] byteArray = dataCollectorPlug.getData();

        String fileName = Utils.DataCollector.getFileName(
                campaignData.getId().toString(),
                String.valueOf(runData.getRunNumber()),
                runData
        );
        Utils.DataCollector.writeStringToFile(fileName, new String(byteArray, StandardCharsets.UTF_8));


        //TODO JAEGER COMMAND PF -> kubectl -n jaeger port-forward jaeger-query-5f9f96c564-6lxkq 16686:16686
        sleep(5);
    }



    private Object buildDataCollectorParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyData : params) {
            jsonObject.put(keyData.getKey(), keyData.getValue());
        }
        jsonObject.put("startTimestamp", runData.getStartTimestamp());
        jsonObject.put("endTimestamp", Utils.Time.getCurrentTimestamp());
        return new DataCollectorParams(jsonObject);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    private void updateRunState(RunStatus runStatus, String message) {
        runData.setStatus(runStatus);
        runData.setMessage(message);
        campaignService.campaignUpdate(campaignData);
    }

    //TODO Utils

    private IjkParams buildIjkParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyValue : params)
            jsonObject.put(keyValue.getKey(), keyValue.getValue());

        return new IjkParams(jsonObject);
    }
}
