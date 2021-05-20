package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;


import lombok.SneakyThrows;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.data.InjectionStatus;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
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
        new Thread(
                () -> {
                    Param param = Utils.jsonToObject(ijkParam.getJsonIjkParam().toString());
                    String command = getCommand(param);
                    if (command == null)
                        throw new RuntimeException("NO INSTRUCTION TO WHAT PROCESS THE INJEKTOR SHOULD TARGET");//TODO PROPER EXCEPTION
                    injectionStatus = InjectionStatus.RUNNING;
                    while (true) {
                        if (injectionStatus == InjectionStatus.STOPPING) {
                            injectionStatus = InjectionStatus.STOPPED;
                            break;
                        }
                        system.sendSSHCommand(command);
                        sleep(param.getInterval());
                    }
                    injectionStatus = InjectionStatus.STOPPING;
                }
        ).start();

    }

    private String getCommand(Param param) {
        if (param.getPid() != null)
            return "kill -9 " + param.getPid();
        else if (param.getProcessName() != null)
            return "killall " + param.getProcessName();
        else
            return null;
    }

    @SneakyThrows
    private void sleep(Integer interval) {
        Thread.sleep(interval * 1000);
    }

    @Override
    public void stopInjection() {
        injectionStatus = InjectionStatus.STOPPING;
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
