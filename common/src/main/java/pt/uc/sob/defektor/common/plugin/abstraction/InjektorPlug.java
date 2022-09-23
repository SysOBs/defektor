package pt.uc.sob.defektor.common.plugin.abstraction;

import pt.uc.sob.defektor.common.config.InjektorParams;
import pt.uc.sob.defektor.common.data.Target;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;

import java.util.List;

public abstract class InjektorPlug<S extends SystemConnectorPlug> {

    protected final S system;

    public InjektorPlug(SystemConnectorPlug system) {
        this.system = (S) system;
    }

    public abstract void help();

    public abstract void performInjection(InjektorParams parameters) throws CampaignException;

    public abstract void stopInjection() throws CampaignException;

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);
}
