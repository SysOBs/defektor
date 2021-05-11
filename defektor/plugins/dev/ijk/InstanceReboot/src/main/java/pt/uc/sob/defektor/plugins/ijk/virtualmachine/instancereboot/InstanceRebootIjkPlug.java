package pt.uc.sob.defektor.plugins.ijk.virtualmachine.instancereboot;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;


public class InstanceRebootIjkPlug extends InjektorPlug<VMSystemPlug> {

    private final String REBOOT_COMMAND = "sudo reboot";
    public InstanceRebootIjkPlug (SystemPlug system) {
        super(system);
    }

    @Override
    public void performInjection(IjkParam param) {
        this.system.sendSSHCommand(REBOOT_COMMAND);
    }

    @Override
    public void stopInjection() {

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
