package pt.uc.sob.defektor.server.campaign;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Orchestrator {

    private final SystemService systemService;
    private final WorkloadGenerator workloadGenerator;

    @Async
    public void conductProcess(PlanData plan) {

        for (InjektionData injektion : plan.getInjektions()) {

            InjektionController injektionController = new InjektionController(
                    injektion.getIjk(),
                    injektion.getWorkLoad(),
                    injektion.getTarget(),
                    getCompatibleSystemList(plan.getSystem().getName()),
                    workloadGenerator,
                    plan.getId()
            );
            injektionController.startCampaign();
        }
    }

    private List<SystemConnectorPlug> getCompatibleSystemList(String systemName) {
        List<SystemConfig> sysConfigList = buildSysConfigList(systemName);
        List<SystemConnectorPlug> systemConnectorPlugs = new ArrayList<>();

        //TODO IMPROVE EXCEPTION
        if (sysConfigList.size() == 0) throw new RuntimeException("No systems configured");

        for (SystemConfig sysConfig : sysConfigList) {
            SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance(systemName, sysConfig);
            systemConnectorPlugs.add(systemConnectorPlug);
        }
        return systemConnectorPlugs;
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
