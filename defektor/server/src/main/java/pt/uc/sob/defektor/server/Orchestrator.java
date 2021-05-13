package pt.uc.sob.defektor.server;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.api.data.KeyData;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.model.*;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;
import pt.uc.sob.defektor.server.utils.Utils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class Orchestrator {

    private final SystemService systemService;

    @Async
    public void conductProcess(Plan plan) {

        for (Injektion injektion : plan.getInjektions()) {

            WorkLoad workLoad = injektion.getWorkLoad();
            WorkloadGenerator workloadGenerator = workloadComposer(workLoad);

            //TODO APPLY LOAD GEN (HAVE TO FIGURE A WAY TO MAKE THIS SYS AGNOSTIC)
            applyLoadGen(workloadGenerator, workLoad.getDuration());

            String systemName = plan.getSystem().getName();
            List<SystemConfig> sysConfigList = buildSysConfigObject(systemName);
            List<SystemPlug> systemPlugs = new ArrayList<>();

            for (SystemConfig sysConfig : sysConfigList) {
                SystemPlug systemPlug = (SystemPlug) SystemPluginFactory.getInstance().getPluginInstance(systemName, sysConfig);
                systemPlugs.add(systemPlug);
            }

            for (SystemPlug systemPlug : systemPlugs){
                buildIjkParams(injektion.getIjk().getParams());
                InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(injektion.getIjk().getName(), systemPlug);
                injektorPlug.performInjection(buildIjkParams(injektion.getIjk().getParams()));
            }

//            sleep(60);

            applyLoadGen(workloadGenerator, workLoad.getDuration());

        }
    }

    private IjkParam buildIjkParams(IjkParams params) {
        JSONObject jsonObject = new JSONObject();
        for(Key key : params.getKey()){
            jsonObject.put(key.getName(), key.getValue());
        }
        return new IjkParam(jsonObject);
    }

    private List<SystemConfig> buildSysConfigObject(String systemName) {

        List<SystemConfig> systemConfigs = new ArrayList<>();
        for (SystemConfigData systemConfigData : systemService.sysConfigListDAO()) {
            if (systemConfigData.getSystemType().getName().equals(systemName)) {
                JSONObject jsonObject = new JSONObject();
                for (KeyData keyData : systemConfigData.getKeyData()) {
                    jsonObject.put(keyData.getName(), keyData.getValue());
                }
                systemConfigs.add(new SystemConfig(jsonObject));
            }
        }
        return systemConfigs;
    }

    private WorkloadGenerator workloadComposer(@Valid WorkLoad workLoad) {

        WorkloadGenerator workLoadGenerator = new WorkloadGenerator();
        workLoadGenerator.setCmd(workLoad.getCmd());
        workLoadGenerator.setDuration(workLoad.getDuration());
        workLoadGenerator.setEnv(workLoad.getEnv());
        workLoadGenerator.setImage(workLoad.getImage());
        workLoadGenerator.setReplicas(workLoad.getReplicas());
        workLoadGenerator.setSlaves(workLoad.getSlaves());

        return workLoadGenerator;
    }

    private void applyLoadGen(WorkloadGenerator workloadGenerator, int loadDuration) {
        //TODO Yet to do
//        workloadGenerator.deployWorkloadGenerator(this.planUUID, this.planTargetNamespace);
//        sleep(loadDuration);
//        WorkloadGenerator.stopWorkLoadGenerator(this.planUUID, this.planTargetNamespace);
    }

    private void defineInjectionType(String ijkName) {
//        ijkTaskHandler = new IjkTaskHandler(ijkName);
//        switch (ijkName) {
//            case "pod-delete":
//                ijkTaskHandler = new IjkTaskHandler("pod-delete");
//                break;
//            case "http-abort":
//                ijkTaskHandler = new IjkTaskHandler("http-abort");
//                break;
//            case "http-delay":
//                ijkTaskHandler = new IjkTaskHandler("http-delay");
//                break;
//            default:
//                ijkTaskHandler = null;
//        }
    }

    private void applyFailureInjection(Ijk ijk) {

        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if (manifestAbsolutePath == null) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

//        ArbitraryYamlInjektor.deployInjection(
//                this.planUUID,
//                this.planTargetNamespace,
//                manifestAbsolutePath
//        );
    }

    private void removeFailureInjection(Ijk ijk) {
        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if (manifestAbsolutePath == null) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

//        ArbitraryYamlInjektor.removeInjection(
//                this.planUUID,
//                this.planTargetNamespace,
//                manifestAbsolutePath
//        );
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
