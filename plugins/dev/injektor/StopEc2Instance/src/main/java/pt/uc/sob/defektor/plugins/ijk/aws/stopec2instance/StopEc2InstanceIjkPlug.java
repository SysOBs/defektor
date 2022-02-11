package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin_interface.InjektorPlug;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.aws.AWSSystemPlug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StopEc2InstanceIjkPlug extends InjektorPlug<AWSSystemPlug> {

    public StopEc2InstanceIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(Map<String, String> parameters) throws CampaignException {
        Parameters params = Utils.mapToObject(parameters);
    }

    @Override
    public void stopInjection() {
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {

        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }
}
