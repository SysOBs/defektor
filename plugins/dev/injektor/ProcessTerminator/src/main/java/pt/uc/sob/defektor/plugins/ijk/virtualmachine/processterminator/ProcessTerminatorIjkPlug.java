package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;


import lombok.SneakyThrows;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;

public class ProcessTerminatorIjkPlug extends InjektorPlug<VMSystemPlug> {

    public ProcessTerminatorIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(IjkParams ijkParams) {
        new Thread(
                () -> {
                    Params params = Utils.jsonToObject(ijkParams.getJsonIjkParams().toString());
                    String command = getCommand(params);
                    if (command == null)
                        throw new RuntimeException("NO INSTRUCTION TO WHAT PROCESS THE INJEKTOR SHOULD TARGET");//TODO PROPER EXCEPTION
                    while (true) {
                        system.sendSSHCommand(command);
                        sleep(params.getInterval());
                    }
                }
        ).start();

    }

    private String getCommand(Params params) {
        if (params.getPid() != null)
            return "kill -9 " + params.getPid();
        else if (params.getProcessName() != null)
            return "killall " + params.getProcessName();
        else
            return null;
    }

    @SneakyThrows
    private void sleep(Integer interval) {
        Thread.sleep(interval * 1000);
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
}
