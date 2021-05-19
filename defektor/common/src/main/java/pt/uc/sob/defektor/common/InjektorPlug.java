package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.data.InjectionStatus;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;

import java.util.List;

public abstract class InjektorPlug <S extends SystemPlug>{

    protected S system;
    protected InjectionStatus injectionStatus = InjectionStatus.STOPPED;

    public InjektorPlug(SystemPlug system) {
        this.system = (S) system;
    }

    public abstract void performInjection(IjkParam param);

    public abstract void stopInjection();

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public InjectionStatus getInjectionStatus() { return injectionStatus; };

    public abstract Class getTheNameOfTheClass();


    //TODO os ijks plugin têm de "conhecer" os system types plugs
    //TODO O injektor plug tem que conhecer que system type é que interopera

}
