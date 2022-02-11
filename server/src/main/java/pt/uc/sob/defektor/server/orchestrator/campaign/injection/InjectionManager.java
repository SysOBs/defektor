package pt.uc.sob.defektor.server.orchestrator.campaign.injection;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.run.FaultInjectionRunManager;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.run.GoldenRunManger;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InjectionManager {

    private final CampaignService campaignService;
    private final GoldenRunManger goldenRunManager;
    private final FaultInjectionRunManager faultInjectionRunManager;

    private List<SystemConnectorPlug> systemConnectorPlugs;
    private InjektionData injektionData;
    private InjectionData injectionData;
    private CampaignData campaignData;

    public void configure(List<SystemConnectorPlug> systemConnectorPlugs, InjektionData injektionData, InjectionData injectionData, CampaignData campaignData) {
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.injektionData = injektionData;
        this.injectionData = injectionData;
        this.campaignData = campaignData;
    }

    public void performInjection() {
        handleCampaignStart();
        injectionData.getRuns().forEach(this::setupRunEnvironment);
        handleCampaignFinish();
    }

    private void handleCampaignStart() {
        log.info(Utils.Logging.Injection.startLogMessage(campaignData, injectionData));
        updateInjectionState(InjectionStatus.RUNNING);
        campaignService.campaignUpdate(campaignData);
    }

    private void setupRunEnvironment(RunData run) {
        injectionData.incrementCurrentRun();

        goldenRunManager.configure(campaignData, injectionData, run, injektionData.getWorkLoad(), injektionData.getDataCollector());
        goldenRunManager.performRun();

        faultInjectionRunManager.configure(
                campaignData,
                injectionData,
                run,
                Utils.getIjkName(injektionData.getIjk()),
                Utils.getIjkData(injektionData.getIjk()),
                systemConnectorPlugs,
                injektionData.getWorkLoad(),
                injektionData.getDataCollector()
        );
        faultInjectionRunManager.performRun();

        campaignService.campaignUpdate(campaignData);
    }

    private void handleCampaignFinish() {
        log.info(Utils.Logging.Injection.finishLogMessage(campaignData, injectionData));
        injectionData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateInjectionState(InjectionStatus.FINISHED);
        campaignService.campaignUpdate(campaignData);
    }

    private void updateInjectionState(InjectionStatus injectionStatus) {
        this.injectionData.setStatus(injectionStatus);
        this.campaignService.campaignUpdate(campaignData);
    }
}