package pt.uc.sob.defektor.server.campaign;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.DataCollectorPlug;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;
import pt.uc.sob.defektor.server.campaign.exception.CampaignException;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CampaignController extends InjektionData {

    private final WorkloadGenerator workloadGenerator;
    private final CampaignService campaignService;
    private List<SystemConnectorPlug> systemConnectorPlugs;
    private InjektorPlug injektorPlug = null;
    private CampaignData campaignData;

    public CampaignController(UUID campaignID, Integer totalRuns, IjkData ijk, WorkLoadData workLoad, DataCollectorData dataCollector, TargetData target, List<SystemConnectorPlug> systemConnectorPlugs, WorkloadGenerator workloadGenerator, CampaignService campaignService) {
        super(totalRuns, ijk, workLoad, dataCollector, target);
        this.systemConnectorPlugs = systemConnectorPlugs;
        this.workloadGenerator = workloadGenerator;
        this.campaignData = new CampaignData(campaignID, totalRuns);
        this.campaignService = campaignService;
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

    private IjkParams buildIjkParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyValue : params)
            jsonObject.put(keyValue.getKey(), keyValue.getValue());
        return new IjkParams(jsonObject);
    }

    private void applyWorkload() throws CampaignException {
        System.out.println(new Date() + " - STARTED WORKLOAD");
        workloadGenerator.performWorkloadGen(this.getWorkLoad(), campaignData.getId());
        sleep(this.getWorkLoad().getDuration());
    }

    private void stopWorkload() throws CampaignException {
        System.out.println(new Date() + " - STOPPED WORKLOAD");
        workloadGenerator.stopWorkloadGen(campaignData.getId());
        sleep(30);
    }

    private void collectData() {
        System.out.println(new Date() + " - COLLECTED DATA");
        //TODO HAVE DATA PLUGIN NAME IN PLAN
        DataCollectorPlug dataCollectorPlug = (DataCollectorPlug) DataCollectorPluginFactory.getInstance().getPluginInstance(this.getDataCollector().getName(), buildDataCollectorParams(this.getDataCollector().getParams()));
        byte[] byteArray = dataCollectorPlug.getData();

        String fileName = "results" + File.separator + campaignData.getId().toString();
        if(campaignData.getStatus() == CampaignStatus.RUNNING_GOLDEN_RUN) {
            fileName += ".GOLDEN_RUN";
        }
        else if(campaignData.getStatus() == CampaignStatus.RUNNING_FAULT_INJECTION_RUN) {
            fileName += ".FAULT_INJECTION_RUN";
        }

        Utils.writeStringToFile(fileName, new String(byteArray, StandardCharsets.UTF_8));

        //TODO JAEGER COMMAND PF -> kubectl -n jaeger port-forward jaeger-query-5f9f96c564-6lxkq 16686:16686
        sleep(5);
    }

    private Object buildDataCollectorParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyData : params) {
            jsonObject.put(keyData.getKey(), keyData.getValue());
        }
        jsonObject.put("startTimestamp", campaignData.getStartTimestamp());
        jsonObject.put("endTimestamp", Utils.Time.getCurrentTimestamp());
        return new DataCollectorParams(jsonObject);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    public void startCampaign() {
        new Thread(
                () -> {
                    handleCampaignStart();
                    while (campaignData.getCurrentRun() <= campaignData.getTotalRuns()) {
                        try {
                            performGoldenRun();
                            performFaultInjectionRun();
                            //TODO PROBLEMA AQUI
                            campaignData.incrementCurrentRun();
                        } catch (CampaignException e) {
                            handleCampaignAbnormallyInterruption(e);
                        }
                    }
                    handleFinishedCampaign();
                }
        ).start();
    }

    private void handleCampaignStart() {
        campaignService.campaignAdd(campaignData);
    }

    private void handleFinishedCampaign() {
        updateCampaignState(
                CampaignStatus.FINISHED,
                Strings.Campaign.FINISHED
        );
    }

    private void handleCampaignAbnormallyInterruption(CampaignException e) {
        updateCampaignState(
                CampaignStatus.ABNORMALLY_INTERRUPTED,
                e.getMessage()
        );
    }

    private void performFaultInjectionRun() throws CampaignException {
        updateCampaignState(
                CampaignStatus.RUNNING_FAULT_INJECTION_RUN,
                Strings.Campaign.RUNNING_FAULT_INJECTION_RUN
        );

        applyWorkload();
        performInjektion();
        stopWorkload();
        stopInjektion();
        collectData();
    }

    private void performGoldenRun() throws CampaignException {
        updateCampaignState(
                CampaignStatus.RUNNING_GOLDEN_RUN,
                Strings.Campaign.RUNNING_GOLDEN_RUN
        );

        applyWorkload();
        stopWorkload();
        collectData();
    }

    private void updateCampaignState(CampaignStatus campaignStatus, String message) {
        this.campaignData.setStatus(campaignStatus);
        this.campaignData.setMessage(message);
        this.campaignService.campaignUpdate(campaignData);
    }
}
