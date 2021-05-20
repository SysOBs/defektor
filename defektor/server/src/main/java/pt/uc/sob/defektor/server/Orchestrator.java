package pt.uc.sob.defektor.server;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class Orchestrator {

    private final SystemService systemService;

    @Async
    public void conductProcess(PlanData plan) {

        for (InjektionData injektion : plan.getInjektions()) {

            InjektionController injektionController = new InjektionController(
                    injektion.getIjk(),
                    injektion.getWorkLoad(),
                    injektion.getTarget(),
                    getCompatibleSystemList(plan.getSystem().getName())
            );
            injektionController.startCampaign();
        }
    }

    private List<SystemPlug> getCompatibleSystemList(String systemName) {
        List<SystemConfig> sysConfigList = buildSysConfigList(systemName);
        List<SystemPlug> systemPlugs = new ArrayList<>();

        //TODO IMPROVE EXCEPTION
        if (sysConfigList.size() == 0) throw new RuntimeException("No systems configured");

        for (SystemConfig sysConfig : sysConfigList) {
            SystemPlug systemPlug = (SystemPlug) SystemPluginFactory.getInstance().getPluginInstance(systemName, sysConfig);
            systemPlugs.add(systemPlug);
        }
        return systemPlugs;
    }


    private List<SystemConfig> buildSysConfigList(String systemName) {
        List<SystemConfig> systemConfigs = new ArrayList<>();
        for (SystemConfigData systemConfigData : systemService.sysConfigList()) {
            if (systemConfigData.getSystemType().getName().equals(systemName)) {
                JSONObject jsonObject = new JSONObject();
                for (KeyValueData keyData : systemConfigData.getConfigs()) {
                    jsonObject.put(keyData.getKey(), keyData.getValue());
                }
                systemConfigs.add(new SystemConfig(jsonObject));
            }
        }
        return systemConfigs;
    }
}
