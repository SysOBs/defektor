package pt.uc.sob.defektor.server.campaign.run.data;

import lombok.SneakyThrows;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.DataCollectorPlug;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RunController {

    private final RunData runData;
    private final CampaignData campaignData;
    private final InjektionData injektionData;
    private final WorkloadGenerator workloadGenerator;
    private final CampaignService campaignService;
    private final List<SystemConnectorPlug> systemConnectorPlugs;
    private InjektorPlug injektorPlug;

    public RunController(RunData runData, CampaignData campaignData, InjektionData injektionData, WorkloadGenerator workloadGenerator, CampaignService campaignService, List<SystemConnectorPlug> systemConnectorPlugs) {
        this.runData = runData;
        this.campaignData = campaignData;
        this.injektionData = injektionData;
        this.workloadGenerator = workloadGenerator;
        this.campaignService = campaignService;
        this.systemConnectorPlugs = systemConnectorPlugs;
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
        System.out.println(new Date() + " - PERFORMING INJEKTION");
        for(SystemConnectorPlug plug : systemConnectorPlugs) {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(injektionData.getIjk().getName(), plug);
            injektorPlug.performInjection(buildIjkParams(injektionData.getIjk().getParams()));
        }
    }

    public void stopInjektion() throws CampaignException {
        System.out.println(new Date() + " - STOPPING INJEKTION");
        injektorPlug.stopInjection();
    }

    private void applyWorkload() throws CampaignException {
        System.out.println(new Date() + " - STARTING WORKLOAD");
        workloadGenerator.performWorkloadGen(injektionData.getWorkLoad(), campaignData.getId(), runData);
        sleep(injektionData.getWorkLoad().getDuration());
    }

    private void stopWorkload() throws CampaignException {
        System.out.println(new Date() + " - STOPPING WORKLOAD");
//        workloadGenerator.stopWorkloadGen(campaignData.getId(), runData);

        String faultOccurrence = injektionData.getIjk().getParams().get(4).getValue();
        Integer duration = injektionData.getWorkLoad().getDuration() / 60;

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
                        injektionData.getDataCollector().getName(),
                        buildDataCollectorParams(
                                injektionData.getDataCollector().getParams()
                        )
                );

        byte[] byteArray = dataCollectorPlug.getData();

        String fileName = Utils.DataCollector.getFileName(
                campaignData.getId().toString(),
                campaignData.getCurrentRun().toString(),
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
