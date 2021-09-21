package pt.uc.sob.defektor.common.pluginterface;

import pt.uc.sob.defektor.common.com.data.InjectionStatus;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.com.exception.CampaignException;

import java.util.List;

public abstract class InjektorPlug <S extends SystemConnectorPlug>{

    protected S system;
    protected volatile InjectionStatus injectionStatus = InjectionStatus.STOPPED;

    public InjektorPlug(SystemConnectorPlug system) {
        this.system = (S) system;
    }

    public abstract void help();

    public abstract void performInjection(IjkParams param) throws CampaignException;

    public abstract void stopInjection() throws CampaignException;

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public InjectionStatus getInjectionStatus() { return injectionStatus; }

}
