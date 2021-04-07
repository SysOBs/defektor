package pt.uc.sob.defektor.common.plugins.ijk.instancereboot;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.common.com.params.ProcessTerminatorParam;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstanceReboot extends InjektorPlug {

    public InstanceReboot(InjektorsManagerInterface injektorsManagerInterface, TaskManagerInterface taskManagerInterface) {
        super(injektorsManagerInterface, taskManagerInterface);
    }

    @Override
    public void performInjection(AbstractParam abstractParam) {
        ProcessTerminatorParam param = (ProcessTerminatorParam) abstractParam;
        String command = null;

        if(param.getPid() != null) {
            command = "kill -9 " + param.getPid();
        }
        else if(param.getPid() != null) {
            command = "killall " + param.getProcessName();
        }

        if(command != null) {
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
