package pt.uc.sob.defektor.plugins.ijk.virtualmachine.instancereboot;

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


public class InstanceRebootIjkPlug extends InjektorPlug<VMSystemPlug> {

    private final String REBOOT_COMMAND = "sudo reboot";

    public InstanceRebootIjkPlug(SystemPlug system) {
        super(system);
    }


    @SneakyThrows
    @Override
    public void performInjection(IjkParam param) {

        Integer interval = Integer.parseInt(
                (String) param.getJsonIjkParam().get("interval"));

        injectionStatus = InjectionStatus.RUNNING;
        while (true) {
            if (injectionStatus == InjectionStatus.STOPPING) {
                injectionStatus = InjectionStatus.STOPPED;
                break;
            }
            system.sendSSHCommand(REBOOT_COMMAND);
            Thread.sleep(interval * 1000);
        }
        injectionStatus = InjectionStatus.STOPPING;
    }

    @Override
    public void stopInjection() {
        injectionStatus = InjectionStatus.STOPPING;
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.INSTANCE);
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
