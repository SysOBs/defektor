package pt.uc.sob.defektor.server.campaign;

import lombok.SneakyThrows;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.DataCollectorPlug;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.expection.CampaignException;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CampaignController extends InjektionData {

    private List<SystemConnectorPlug> systemConnectorPlugs;
    private InjektorPlug injektorPlug = null;
    private DataCollectorPlug dataCollectorPlug = null;
    private final WorkloadGenerator workloadGenerator;
    private CampaignData campaignData;

    public CampaignController(UUID campaignID, Integer totalRuns, IjkData ijk, WorkLoadData workLoad, TargetData target, List<SystemConnectorPlug> systemConnectorPlugs, WorkloadGenerator workloadGenerator) {
        super(totalRuns, ijk, workLoad, target);
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.workloadGenerator = workloadGenerator;
        this.campaignData = new CampaignData(campaignID, totalRuns);
    }

    public void performInjektion() {
        System.out.println(new Date() + " - PERFORMED INJEKTION");
        systemConnectorPlugs.forEach(systemPlug -> {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(this.getIjk().getName(), systemPlug);
            injektorPlug.performInjection(buildIjkParams(this.getIjk().getParams()));
        });
    }

    public void stopInjektion() {
        System.out.println(new Date() + " - STOPPED INJEKTION");
        injektorPlug.stopInjection();
    }

    private IjkParam buildIjkParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyValue : params)
            jsonObject.put(keyValue.getKey(), keyValue.getValue());
        return new IjkParam(jsonObject);
    }

    private void applyWorkload() throws CampaignException {
        System.out.println(new Date() + " - STARTED WORKLOAD");
        workloadGenerator.performWorkloadGen(this.getWorkLoad(), campaignData.getId());
        sleep(this.getWorkLoad().getDuration());
    }

    private void stopWorkload() throws CampaignException {
        System.out.println(new Date() + " - STOPPED WORKLOAD");
        workloadGenerator.stopWorkloadGen(campaignData.getId());
        sleep(1);
    }

    private void collectData() {
        System.out.println(new Date() + " - COLLECTED DATA");
        dataCollectorPlug = (DataCollectorPlug) DataCollectorPluginFactory.getInstance().getPluginInstance(this.getIjk().getName());
        dataCollectorPlug.getData();

        //TODO JAEGER COMMAND PF -> kubectl -n jaeger port-forward jaeger-query-5f9f96c564-6lxkq 16686:16686
        sleep(5);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    public void startCampaign() {
        new Thread(
                () -> {
                    while (campaignData.getCurrentRun() <= campaignData.getTotalRuns()) {
                        try {
                            performGoldenRun();
                            performFaultInjectionRun();
                        } catch (CampaignException e) {
                            handleCampaignAbnormallyInterruption(e);
                        }
                        finally {
                            campaignData.incrementCurrentRun();
                        }
                    }
                    campaignData.setStatus(CampaignStatus.FINISHED);
                }
        ).start();
    }

    private void handleCampaignAbnormallyInterruption(CampaignException e) {
        campaignData.setStatus(CampaignStatus.ABNORMALLY_INTERRUPTED);
        System.out.printf(e.getMessage());
    }

    private void performFaultInjectionRun() throws CampaignException {
        campaignData.setStatus(CampaignStatus.RUNNING_GOLDEN_RUN);
        applyWorkload();
        performInjektion();
        stopWorkload();
        stopInjektion();
        collectData();
    }

    private void performGoldenRun() throws CampaignException {
        campaignData.setStatus(CampaignStatus.RUNNING_FAULT_INJECTION_RUN);
        applyWorkload();
        stopWorkload();
        collectData();
    }
}
