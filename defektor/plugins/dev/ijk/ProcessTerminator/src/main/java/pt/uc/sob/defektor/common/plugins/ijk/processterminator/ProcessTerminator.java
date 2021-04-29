package pt.uc.sob.defektor.common.plugins.ijk.processterminator;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.common.com.params.ProcessTerminatorParam;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.system.virtualmachine.VMSystemPlug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessTerminator extends InjektorPlug<VMSystemPlug> {

    public ProcessTerminator(VMSystemPlug system) {
        super(system);
    }
    
    public ProcessTerminator(SystemPlug system) {
        super((VMSystemPlug) system);
    }

    @Override
    public void performInjection(AbstractParam abstractParam) {
        ProcessTerminatorParam param = (ProcessTerminatorParam) abstractParam;
        String command = null;

        if(param.getPid() != null)
            command = "kill -9 " + param.getPid();
        else if(param.getProcessName() != null)
            command = "killall " + param.getProcessName();

        this.system.sendSshCommand(command);
    }

    @Override
    public void stopInjection() {
        
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.PROCESS);
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
