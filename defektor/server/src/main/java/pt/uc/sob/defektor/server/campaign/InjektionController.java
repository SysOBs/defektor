package pt.uc.sob.defektor.server.campaign;

import lombok.SneakyThrows;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InjektionController extends InjektionData {

    private List<SystemPlug> systemPlugs;
    private InjektorPlug injektorPlug = null;
    private final WorkloadGenerator workloadGenerator;
    private final UUID campaignID;


    public InjektionController(IjkData ijk, WorkLoadData workLoad, TargetData target, List<SystemPlug> systemPlugs, WorkloadGenerator workloadGenerator, UUID campaignID) {
        super(ijk, workLoad, target);
        this.systemPlugs = systemPlugs;
        this.workloadGenerator = workloadGenerator;
        this.campaignID = campaignID;
    }

    public void performInjektion() {
        System.out.println(new Date() + " - PERFORMED INJEKTION");
        systemPlugs.forEach(systemPlug -> {
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

    private void applyWorkload() {
        System.out.println(new Date() + " - STARTED WORKLOAD");
        workloadGenerator.performWorkloadGen(this.getWorkLoad(), campaignID);
        sleep(this.getWorkLoad().getDuration());
    }

    private void stopWorkload() {
        System.out.println(new Date() + " - STOPPED WORKLOAD");
        workloadGenerator.stopWorkloadGen(campaignID);
        sleep(1);
    }

    private void collectData() {
        System.out.println(new Date() + " - COLLECTED DATA");
        sleep(5);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    public void startCampaign() {
        new Thread(
                () -> {
                    applyWorkload();
                    stopWorkload();
                    collectData();
                    performInjektion();
                    applyWorkload();
                    stopWorkload();
                    stopInjektion();
                    collectData();
                }
        ).start();
    }
}
