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
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class RunManager {

    private final WorkloadGenerator workloadGenerator;
    private final CampaignService campaignService;

    private final Integer COOLDOWN_TIME = 30;

    private CampaignData campaignData;
    private List<SystemConnectorPlug> systemConnectorPlugs;
    private InjectionData injectionData;
    private RunData runData;
    private InjektorPlug injektorPlug;
    private IjkData ijk;
    private WorkLoadData workLoad;
    private DataCollectorData dataCollector;

    public void configure(CampaignData campaignData, InjektionData injektion, List<SystemConnectorPlug> systemConnectorPlugs, InjectionData injectionData, RunData run) {
        this.campaignData = campaignData;
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.injectionData = injectionData;
        this.runData = run;
        this.ijk = injektion.getIjk();
        this.workLoad = injektion.getWorkLoad();
        this.dataCollector = injektion.getDataCollector();
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
        log.info(Utils.Logging.Run.startRun(campaignData, injectionData, runData));
        runData.setStartTimestamp(Utils.Time.getCurrentTimestamp());

    }

    private void handleRunFinish() {
        log.info(Utils.Logging.Run.finishRun(campaignData, injectionData, runData));
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
        log.error(Utils.Logging.Run.abnormallyInterrupted(campaignData, injectionData, runData, e.getMessage()));
        runData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateRunState(
                RunStatus.ABNORMALLY_INTERRUPTED,
                e.getMessage()
        );
    }

    public void performInjektion() throws CampaignException {
        log.info(Utils.Logging.Run.performInjektion(campaignData, injectionData, runData, ijk.getName()));
        for(SystemConnectorPlug plug : systemConnectorPlugs) {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(ijk.getName(), plug);
            injektorPlug.performInjection(buildIjkParams(ijk.getParams()));
        }
    }

    public void stopInjektion() throws CampaignException {
        log.info(Utils.Logging.Run.stopInjektion(campaignData, injectionData, runData));
        injektorPlug.stopInjection();
    }

    private void applyWorkload() throws CampaignException {
        log.info(Utils.Logging.Run.applyWorkload(campaignData, injectionData, runData, workLoad.getDuration()));
        workloadGenerator.performWorkloadGen(workLoad, campaignData.getId(), runData);
        sleep(workLoad.getDuration());
    }

    private void stopWorkload() throws CampaignException {
        log.info(Utils.Logging.Run.stopWorkload(campaignData, injectionData, runData));

        String faultOccurrence = ijk.getParams().get(4).getValue();
        Integer duration = workLoad.getDuration() / 60;
        workloadGenerator.stopWorkloadGen(
                campaignData.getId(),
                runData,
                faultOccurrence,
                duration
        );
    }

    private void collectData() throws CampaignException {
        sleep(COOLDOWN_TIME);
        log.info(Utils.Logging.Run.collectData(campaignData, injectionData, runData));
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

    private IjkParams buildIjkParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyValue : params)
            jsonObject.put(keyValue.getKey(), keyValue.getValue());

        return new IjkParams(jsonObject);
    }
}
