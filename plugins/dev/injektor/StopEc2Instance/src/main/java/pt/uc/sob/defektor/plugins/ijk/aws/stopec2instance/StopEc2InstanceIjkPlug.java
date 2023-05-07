package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import pt.uc.sob.defektor.common.config.InjektorParams;
import pt.uc.sob.defektor.common.data.Target;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.aws.AwsSystemPlug;
import java.util.ArrayList;
import java.util.List;

public class StopEc2InstanceIjkPlug extends InjektorPlug<AwsSystemPlug> {

    public StopEc2InstanceIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(InjektorParams parameters) throws CampaignException {
        Parameters params = Utils.jsonToObject(parameters.toString());
    }

    @Override
    public void stopInjection() {
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(new EC2Target());
            }
        };

    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }
}
