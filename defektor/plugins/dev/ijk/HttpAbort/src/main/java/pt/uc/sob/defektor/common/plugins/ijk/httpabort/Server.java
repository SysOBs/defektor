package pt.uc.sob.defektor.common.plugins.ijk.httpabort;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.util.ArrayList;
import java.util.List;

public class Server extends InjektorPlug {

    public Server(InjektorsManagerInterface injektorsManagerInterface, TaskManagerInterface taskManagerInterface) {
        super(injektorsManagerInterface, taskManagerInterface);
    }

    @Override
    public void performInjection() {
        System.out.println("The injection will be started and performed in this method belonging to plugin: " + this.getClass().getCanonicalName());
    }

    @Override
    public void setup() {

    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.POD);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

    @Override
    public Class getTheNameOfTheClass() {
        return null;
    }

}
