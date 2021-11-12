package pt.uc.sob.defektor.server.orchestrator.campaign;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.InjectionData;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.InjectionController;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignManager {

    private final CampaignService campaignService;
    private final InjectionController injectionController;

    private List < SystemConnectorPlug > compatibleSystemList;
    private List < InjektionData > injektionDataList;
    private CampaignData campaignData;

    public void configure(List < SystemConnectorPlug > compatibleSystemList, CampaignData campaign, List < InjektionData > injektionData) {
        this.compatibleSystemList = compatibleSystemList;
        this.campaignData = campaign;
        this.injektionDataList = injektionData;
    }

    public void performCampaign() {
        handleCampaignStart();

        for (int i = 0; i < injektionDataList.size(); i++) {
            this.setupInjectionEnvironment(
                    injektionDataList.get(i),
                    campaignData.getInjections().get(i)
            );
        }

        handleCampaignFinish();
    }

    private void handleCampaignStart() {
        log.info(Utils.Logging.Campaign.startLogMessage(campaignData));
        allocateMemoryToAllInjectionsAndRuns();
        updateCampaignState(CampaignStatus.RUNNING);
        campaignService.campaignAdd(campaignData);
    }

    private void allocateMemoryToAllInjectionsAndRuns() {
        List < InjectionData > injectionList = new ArrayList < > ();

        for (int i = 1; i <= campaignData.getTotalInjections(); i++) {
            InjectionData injectionData = new InjectionData(i);

            for (int j = 1; j <= injectionData.getTotalRuns(); j++) {
                List < RunData > runDataList = new ArrayList < > ();
                runDataList.add(new RunData(j));
                injectionData.setRuns(runDataList);
            }
            injectionList.add(new InjectionData(i));
        }

        this.campaignData.setInjections(injectionList);
    }

    private void setupInjectionEnvironment(InjektionData injektionData, InjectionData injectionData) {
        campaignData.incrementCurrentInjection();
        injectionController.configure(compatibleSystemList, injektionData, injectionData, campaignData);
        injectionController.performInjection();
        campaignService.campaignUpdate(campaignData);
    }

    private void handleCampaignFinish() {
        log.info(Utils.Logging.Campaign.finishLogMessage(campaignData));
        campaignData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateCampaignState(CampaignStatus.FINISHED);
    }

    private void updateCampaignState(CampaignStatus campaignStatus) {
        this.campaignData.setStatus(campaignStatus);
        this.campaignService.campaignUpdate(campaignData);
    }
}