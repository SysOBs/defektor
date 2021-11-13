package pt.uc.sob.defektor.server.orchestrator;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.orchestrator.campaign.CampaignManager;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;
import pt.uc.sob.defektor.server.utils.Strings;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Async
public class Orchestrator {

    private final CampaignManager campaignManager;
    private final SystemService systemService;

    public void startInjectionCampaign(PlanData plan) throws InvalidSystemException {

        List<SystemConnectorPlug> compatibleSystemList = getCompatibleSystemList(plan.getSystem().getName());
        CampaignData campaign = new CampaignData(plan.getId(), plan.getName());
        List<InjektionData> injektionList = plan.getInjektions();

        campaignManager.configure(compatibleSystemList, campaign, injektionList);
        campaignManager.performCampaign();
    }

    private List<SystemConnectorPlug> getCompatibleSystemList(String systemName) throws InvalidSystemException {
        List<SystemConfigs> sysConfigList = buildSysConfigList(systemName);

        if (sysConfigList.size() == 0)
            throw new InvalidSystemException(Strings.Errors.NO_SYSTEMS_CONFIGURED);


        List<SystemConnectorPlug> systemConnectorPlugs = new ArrayList<>();

        for (SystemConfigs sysConfig : sysConfigList) {
            systemConnectorPlugs.add(
                    (SystemConnectorPlug) SystemConnectorPluginFactory
                            .getInstance()
                            .getPluginInstance(systemName, sysConfig)
            );
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