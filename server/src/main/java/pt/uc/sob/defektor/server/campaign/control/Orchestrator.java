package pt.uc.sob.defektor.server.campaign.control;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;
import pt.uc.sob.defektor.server.api.service.CampaignService;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.campaign.workloadgen.WorkloadGenerator;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Orchestrator {

    private final SystemService systemService;
    private final CampaignService campaignService;
    private final WorkloadGenerator workloadGenerator;

    public void conductProcess(PlanData plan) throws InvalidSystemException {

        for (InjektionData injektionData : plan.getInjektions()) {
            new CampaignController(
                    new CampaignData(plan.getId(), injektionData.getTotalRuns()),
                    getCompatibleSystemList(plan.getSystem().getName()),
                    workloadGenerator,
                    campaignService,
                    injektionData
            ).startCampaign();
        }
    }

    private List<SystemConnectorPlug> getCompatibleSystemList(String systemName) throws InvalidSystemException {
        List<SystemConfigs> sysConfigList = buildSysConfigList(systemName);
        List<SystemConnectorPlug> systemConnectorPlugs = new ArrayList<>();

        if (sysConfigList.size() == 0)
            throw new InvalidSystemException(Strings.Errors.NO_SYSTEMS_CONFIGURED);

        for (SystemConfigs sysConfig : sysConfigList) {
            SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance(systemName, sysConfig);
            systemConnectorPlugs.add(systemConnectorPlug);
        }
        return systemConnectorPlugs;
    }


    private List<SystemConfigs> buildSysConfigList(String systemName) {
        List<SystemConfigs> systemConfigs = new ArrayList<>();
        for (SystemConfigData systemConfigData : systemService.sysConfigList()) {
            if (systemConfigData.getSystemType().getName().equals(systemName)) {
                JSONObject jsonObject = new JSONObject();
                for (KeyValueData keyData : systemConfigData.getConfigs()) {
                    jsonObject.put(keyData.getKey(), keyData.getValue());
                }
                systemConfigs.add(new SystemConfigs(jsonObject));
            }
        }
        return systemConfigs;
    }
}
