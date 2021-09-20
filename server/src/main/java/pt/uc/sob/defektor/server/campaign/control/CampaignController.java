package pt.uc.sob.defektor.server.campaign.control;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;
import pt.uc.sob.defektor.server.campaign.run.data.RunController;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class CampaignController {

    private final WorkloadGenerator workloadGenerator;
    private final CampaignService campaignService;
    private List<SystemConnectorPlug> systemConnectorPlugs;
    private InjektionData injektionData;
    private RunController runController;
    private CampaignData campaignData;

    public CampaignController(CampaignData campaignData, List<SystemConnectorPlug> systemConnectorPlugs, WorkloadGenerator workloadGenerator, CampaignService campaignService, InjektionData injektionData) {
        this.campaignData = campaignData;
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.workloadGenerator = workloadGenerator;
        this.injektionData = injektionData;
        this.campaignService = campaignService;
    }

    public void startCampaign() {
        new Thread(
                () -> {
                    handleCampaignStart();
                    for (int i = 0; i < campaignData.getTotalRuns(); ++i) {
                        setupRunEnvironment();
                        runController.performRun();
                    }
                    handleCampaignFinish();
                }
        ).start();
    }

    private void handleCampaignStart() {
        campaignService.campaignAdd(campaignData);
        System.out.println(new Date() + " - STARTED CAMPAIGN");
    }

    private void setupRunEnvironment() {
        campaignData.incrementCurrentRun();

        RunData runData = new RunData(campaignData.getCurrentRun());
        campaignData.getRuns().add(runData);

        runController = new RunController(
                runData,
                campaignData,
                injektionData,
                workloadGenerator,
                campaignService,
                systemConnectorPlugs
        );
        campaignService.campaignUpdate(campaignData);

    }

    private void handleCampaignFinish() {
        updateCampaignState(
                CampaignStatus.FINISHED
        );
        campaignData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        System.out.println(new Date() + " - FINISHED CAMPAIGN");
    }

    private void updateCampaignState(CampaignStatus campaignStatus) {
        this.campaignData.setStatus(campaignStatus);
        this.campaignService.campaignUpdate(campaignData);
    }

}
