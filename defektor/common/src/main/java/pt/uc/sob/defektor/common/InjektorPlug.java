package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.util.List;

public abstract class InjektorPlug <S extends SystemPlug>{

    protected InjektorsManagerInterface injektorsManagerInterface;
    protected TaskManagerInterface taskManagerInterface;

    public InjektorPlug(InjektorsManagerInterface injektorsManagerInterface, TaskManagerInterface taskManagerInterface) {
        this.injektorsManagerInterface = injektorsManagerInterface;
        this.taskManagerInterface = taskManagerInterface;
    }

    public abstract void performInjection(/* TODO PARAMS */);

    public abstract void setup();

    public abstract List<TargetType> getTargetTypes();

    public abstract List<Target> getTargetInstancesByType(TargetType targetType);

    public abstract Class getTheNameOfTheClass();

    //TODO os ijks plugin têm de "conhecer" os system types plugs
    //TODO O injektor plug tem que conhecer que system type é que interopera

}
