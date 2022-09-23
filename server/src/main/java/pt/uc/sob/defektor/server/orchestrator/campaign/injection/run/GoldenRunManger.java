package pt.uc.sob.defektor.server.orchestrator.campaign.injection.run;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.exception.CampaignException;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

@Log4j2
@Service
public class GoldenRunManger extends RunManager {

    public GoldenRunManger(WorkloadGenerator workloadGenerator, CampaignService campaignService) {
        super(workloadGenerator, campaignService);
    }

    @Override
    public void performRun() {
        handleRunStart();
        updateRunState(RunStatus.RUNNING_GOLDEN_RUN, Strings.Run.RUNNING_GOLDEN_RUN);

        try {
            applyWorkload();
            stopWorkload();
            collectData();
        } catch (CampaignException e) {
            handleAbnormallyInterruptedRun(e);
        }
    }

    @Override
    protected void handleRunStart() {
        log.info(Utils.Logging.Run.startRun(campaignData, injectionData, runData));
        this.runData.setStartTimestamp(Utils.Time.getCurrentTimestamp());
    }

    @Override
    protected void handleRunFinish() {

    }
}
