package pt.uc.sob.defektor.common.plugin.abstraction;

import pt.uc.sob.defektor.common.config.SystemConfig;
import pt.uc.sob.defektor.common.data.FaultType;
import pt.uc.sob.defektor.common.data.Target;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;

import java.util.List;

public abstract class InjektorPlug {

    protected SystemConnectorPlug system;
    protected SystemConfig config;

    public InjektorPlug(SystemConfig config){
        this.config = config;
    }

    public abstract void help();

    public abstract void performInjection() throws CampaignException;

    public abstract void stopInjection() throws CampaignException;

    public abstract List<TargetType> getTargetTypes();

    public abstract List<FaultType> getFaultTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public abstract String getName();
}
