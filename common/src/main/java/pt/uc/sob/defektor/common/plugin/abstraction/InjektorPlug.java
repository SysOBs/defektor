package pt.uc.sob.defektor.common.plugin.abstraction;

import pt.uc.sob.defektor.common.config.InjektorParams;
import pt.uc.sob.defektor.common.data.FaultType;
import pt.uc.sob.defektor.common.data.Target;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;

import java.util.List;

public abstract class InjektorPlug {

    protected final SystemConnectorPlug system;

    public InjektorPlug(SystemConnectorPlug system) {
        this.system = system;
    }

    public abstract void help();

    public abstract void performInjection(InjektorParams parameters) throws CampaignException;

    public abstract void stopInjection() throws CampaignException;

    public abstract List<TargetType> getTargetTypes();

    public abstract List<FaultType> getFaultTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public abstract String getName();
}
