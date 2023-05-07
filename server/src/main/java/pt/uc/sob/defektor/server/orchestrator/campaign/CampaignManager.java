package pt.uc.sob.defektor.server.orchestrator.campaign;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.expection.InvalidPlanException;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.InjectionManager;
import pt.uc.sob.defektor.server.pluginization.PluginRegistry;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class CampaignManager {

    private final CampaignService campaignService;
    private final InjectionManager injectionManager;

    private List<SystemConnectorPlug> compatibleSystemList;
    private List<InjektionData> injektionList;
    private CampaignData campaignData;
    private PluginRegistry ijkRegistry;

    private List<IjkData> injektors;


    public void configure(List<SystemConnectorPlug> compatibleSystemList, CampaignData campaign, List<InjektionData> injektionData, List<IjkData> ijks) {
        this.compatibleSystemList = compatibleSystemList;
        this.campaignData = campaign;
        this.injektionList = injektionData;
        this.ijkRegistry = new PluginRegistry();
        this.injektors = ijks;

        injektionList.forEach(i -> {
            var name = i.getIjkName();
            // TODO not sure how this deals with the fact that no SystemConnector is plugged
            var ijk = IjkPluginFactory.getInstance().getPluginInstance(name);
            ijkRegistry.RegisterInjektor((InjektorPlug)ijk);
        });
    }

    public void performCampaign() {
        handleCampaignStart();

        for (int i = 0; i < injektionList.size(); i++) {
            this.setupInjectionEnvironment(
                    injektionList.get(i),
                    campaignData.getInjections().get(i),
                    this.injektors
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
        List<InjectionData> injectionList = new ArrayList<>();

        for (InjektionData injektion : injektionList) {
            Integer injectionNumber = injektionList.indexOf(injektion);
            Integer totalRuns = injektion.getTotalRuns();
            InjectionData injection = new InjectionData(injectionNumber, totalRuns);

            IntStream.range(0, injection.getTotalRuns()).forEach(value -> {
                List<RunData> runDataList = new ArrayList<>();
                runDataList.add(new RunData(value));
                injection.setRuns(runDataList);
            });

            injectionList.add(injection);
        }

        this.campaignData.setInjections(injectionList);
    }

    private void setupInjectionEnvironment(InjektionData injektionData, InjectionData injectionData, List<IjkData> ijks) {
        campaignData.incrementCurrentInjection();
        injectionManager.configure(compatibleSystemList, injektionData, injectionData, campaignData, ijks);
        injectionManager.performInjection();
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
