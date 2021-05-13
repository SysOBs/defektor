package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;

public class ProcessTerminatorIjkPlug extends InjektorPlug<VMSystemPlug> {

    public ProcessTerminatorIjkPlug(SystemPlug system) {
        super(system);
    }

    @Override
    public void performInjection(IjkParam ijkParam) {
        Param param = Utils.jsonToObject(ijkParam.getJsonIjkParam().toString());
        String command = null;

        if(param.getPid() != null)
            command = "kill -9 " + param.getPid();
        else if(param.getProcessName() != null)
            command = "killall " + param.getProcessName();

        //TODO if diferente de null
        this.system.sendSSHCommand(command);
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
