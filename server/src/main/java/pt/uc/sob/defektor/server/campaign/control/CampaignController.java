package pt.uc.sob.defektor.server.campaign.control;

import lombok.RequiredArgsConstructor;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.CampaignData;
import pt.uc.sob.defektor.server.api.data.InjektionData;
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
                    for (RunData run : campaignData.getRuns()) {
                        setupRunEnvironment(run);
                        runController.performRun();
                    }
                    handleCampaignFinish();
                }
        ).start();
    }

    private void handleCampaignStart() {
        allocateMemoryToAllRuns();
        campaignService.campaignAdd(campaignData);
        System.out.println(new Date() + " - STARTED CAMPAIGN");
    }

    private void allocateMemoryToAllRuns() {
        //ALLOCATE MEMORY TO ALL RUNS
        List<RunData> runDataList = new ArrayList<>();
        for (int i = 1; i <= campaignData.getTotalRuns(); i++) {
            runDataList.add(new RunData(i));
        }
        this.campaignData.setRuns(runDataList);
    }

    private void setupRunEnvironment(RunData run) {
        campaignData.incrementCurrentRun();

        runController = new RunController(
                run,
                campaignData,
                injektionData,
                workloadGenerator,
                campaignService,
                systemConnectorPlugs
        );
        campaignService.campaignUpdate(campaignData);
    }

    private void handleCampaignFinish() {
        System.out.println(new Date() + " - FINISHED CAMPAIGN");
        campaignData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateCampaignState(
                CampaignStatus.FINISHED
        );
    }

    private void updateCampaignState(CampaignStatus campaignStatus) {
        this.campaignData.setStatus(campaignStatus);
        this.campaignService.campaignUpdate(campaignData);
    }

}
