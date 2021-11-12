package pt.uc.sob.defektor.server.orchestrator.campaign.injection;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.InjectionData;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.run.RunController;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InjectionController {

    private final CampaignService campaignService;
    private final RunController runController;

    private List < SystemConnectorPlug > systemConnectorPlugs;
    private InjektionData injektionData;
    private InjectionData injectionData;
    private CampaignData campaignData;

    public void configure(List < SystemConnectorPlug > compatibleSystemList, InjektionData injektionData, InjectionData injectionData, CampaignData campaignData) {
        this.systemConnectorPlugs = compatibleSystemList;
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
        runController.configure(campaignData, injektionData, systemConnectorPlugs, run);
        runController.performRun();
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