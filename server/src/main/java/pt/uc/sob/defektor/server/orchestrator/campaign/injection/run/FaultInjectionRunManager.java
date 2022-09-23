package pt.uc.sob.defektor.server.orchestrator.campaign.injection.run;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.List;

@Log4j2
@Service
public class FaultInjectionRunManager extends RunManager {

    private List<SystemConnectorPlug> systemConnectorPlugs;
    private String ijkName;
    private IjkData ijk;
    private InjektorPlug injektorPlug;

    public FaultInjectionRunManager(WorkloadGenerator workloadGenerator, CampaignService campaignService) {
        super(workloadGenerator, campaignService);
    }

    @Override
    public void performRun() {
        updateRunState(RunStatus.RUNNING_FAULT_INJECTION_RUN, Strings.Run.RUNNING_FAULT_INJECTION_RUN);
        try {

            performInjektion();
            applyWorkload();
            stopWorkload();
            stopInjektion();
            collectData();

            handleRunFinish();
        } catch (CampaignException e) {
            handleAbnormallyInterruptedRun(e);
        }
    }

    @Override
    protected void handleRunStart() {
    }

    @Override
    protected void handleRunFinish() {
        log.info(Utils.Logging.Run.finishRun(campaignData, injectionData, runData));
        runData.setEndTimestamp(Utils.Time.getCurrentTimestamp());
        updateRunState(RunStatus.FINISHED, Strings.Run.FINISHED);
    }

    public void configure(CampaignData campaignData, InjectionData injectionData, RunData runData, String ijkName, IjkData ijkData, List<SystemConnectorPlug> systemConnectorPlugs, WorkLoadData workLoad, DataCollectorData dataCollector) {
        super.configure(campaignData, injectionData, runData, workLoad, dataCollector);
        this.ijk = ijkData;
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.ijkName = ijkName;
    }

    private void performInjektion() throws CampaignException {
        log.info(Utils.Logging.Run.performInjektion(campaignData, injectionData, runData, ijkName));
        for (SystemConnectorPlug plug : systemConnectorPlugs) {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(ijkName, plug);
            injektorPlug.performInjection(ijk.getParameters());
        }
    }

    private void stopInjektion() throws CampaignException {
        log.info(Utils.Logging.Run.stopInjektion(campaignData, injectionData, runData));
        injektorPlug.stopInjection();
    }
}
