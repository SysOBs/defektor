package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.data.InjectionStatus;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;

import java.util.List;

public abstract class InjektorPlug <S extends SystemConnectorPlug>{

    protected S system;
    protected volatile InjectionStatus injectionStatus = InjectionStatus.STOPPED;

    public InjektorPlug(SystemConnectorPlug system) {
        this.system = (S) system;
    }

    public abstract void help();

    public abstract void performInjection(IjkParam param);

    public abstract void stopInjection();

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public InjectionStatus getInjectionStatus() { return injectionStatus; };

}
