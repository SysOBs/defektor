package pt.uc.sob.defektor.common.plugin_interface;

import pt.uc.sob.defektor.common.Parameters;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;

import java.util.List;
import java.util.Map;

public abstract class InjektorPlug<S extends SystemConnectorPlug> {

    protected final S system;

    public InjektorPlug(SystemConnectorPlug system) {
        this.system = (S) system;
    }

    public abstract void help();

    public abstract void performInjection(Parameters parameters) throws CampaignException;

    public abstract void stopInjection() throws CampaignException;

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);
}
