package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;

import java.util.List;

public abstract class InjektorPlug <S extends SystemPlug>{

    protected S system;

    public InjektorPlug(S system) {
        this.system = system;
    }

    public abstract void performInjection(AbstractParam param);

    public abstract void stopInjection();

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public abstract Class getTheNameOfTheClass();

    //TODO os ijks plugin têm de "conhecer" os system types plugs
    //TODO O injektor plug tem que conhecer que system type é que interopera

}
