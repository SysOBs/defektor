package pt.uc.sob.defektor.server.orchestrator.campaign.injection.run;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin_interface.DataCollectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Log4j2
@RequiredArgsConstructor
public abstract class RunManager {

    protected final WorkloadGenerator workloadGenerator;
    protected final CampaignService campaignService;
    private final Integer COOLDOWN_TIME = 30;
    protected CampaignData campaignData;
    protected InjectionData injectionData;
    protected RunData runData;
    protected WorkLoadData workLoad;
    protected DataCollectorData dataCollector;

    public void configure(CampaignData campaignData, InjectionData injectionData, RunData runData, WorkLoadData workLoadData, DataCollectorData dataCollector) {
        this.campaignData = campaignData;
        this.injectionData = injectionData;
        this.runData = runData;
        this.workLoad = workLoadData;
        this.dataCollector = dataCollector;
    }

    public abstract void performRun() throws CampaignException;

    protected abstract void handleRunStart();

    protected abstract void handleRunFinish();


    protected void handleAbnormallyInterruptedRun(CampaignException e) {
        log.error(Utils.Logging.Run.abnormallyInterrupted(campaignData, injectionData, runData, e.getMessage()));
        runData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateRunState(RunStatus.ABNORMALLY_INTERRUPTED, e.getMessage());
    }

    protected void applyWorkload() throws CampaignException {
        log.info(Utils.Logging.Run.applyWorkload(campaignData, injectionData, runData, workLoad.getDuration()));
        workloadGenerator.performWorkloadGen(workLoad, campaignData.getId(), runData);
        sleep(workLoad.getDuration());
    }

    protected void stopWorkload() throws CampaignException {
        log.info(Utils.Logging.Run.stopWorkload(campaignData, injectionData, runData));
        workloadGenerator.stopWorkloadGen(campaignData.getId(), runData);
    }

    protected void collectData() throws CampaignException {
        sleep(COOLDOWN_TIME);
        log.info(Utils.Logging.Run.collectData(campaignData, injectionData, runData));
        DataCollectorPlug dataCollectorPlug = (DataCollectorPlug) DataCollectorPluginFactory
                .getInstance()
                .getPluginInstance(
                        dataCollector.getName(),
                        dataCollector.getParameters()
                );

        byte[] byteArray = dataCollectorPlug.getData();

        String fileName = Utils.DataCollector.getFileName(
                campaignData.getId().toString(),
                String.valueOf(runData.getRunNumber()),
                runData
        );
        Utils.DataCollector.writeStringToFile(fileName, new String(byteArray, StandardCharsets.UTF_8));
    }

    protected void updateRunState(RunStatus runStatus, String message) {
        runData.setStatus(runStatus);
        runData.setMessage(message);
        campaignService.campaignUpdate(campaignData);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }
}
