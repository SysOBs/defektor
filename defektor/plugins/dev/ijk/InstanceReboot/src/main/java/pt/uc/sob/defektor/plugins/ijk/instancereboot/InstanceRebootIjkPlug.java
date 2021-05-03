package pt.uc.sob.defektor.plugins.ijk.instancereboot;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.InstanceRebootParam;
import pt.uc.sob.defektor.common.com.params.ParamInterface;
import pt.uc.sob.defektor.common.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;


public class InstanceRebootIjkPlug extends InjektorPlug<VMSystemPlug> {

    public InstanceRebootIjkPlug(VMSystemPlug system) {
        super(system);
    }

    @Override
    public void performInjection(ParamInterface abstractParam) {
        InstanceRebootParam param = (InstanceRebootParam) abstractParam;
        this.system.sendSshCommand(param.getCommand());
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
