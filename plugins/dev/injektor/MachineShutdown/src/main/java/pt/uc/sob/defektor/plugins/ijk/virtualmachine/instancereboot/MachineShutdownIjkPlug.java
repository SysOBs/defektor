package pt.uc.sob.defektor.plugins.ijk.virtualmachine.instancereboot;

import lombok.SneakyThrows;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;


public class MachineShutdownIjkPlug extends InjektorPlug<VMSystemPlug> {

    private final String REBOOT_COMMAND = "sudo shutdown";

    public MachineShutdownIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(IjkParams ijkParams) {
        Param param = Utils.jsonToObject(ijkParams.getJsonIjkParams().toString());
        new Thread(
                () -> {
                    while (true) {
                        system.sendSSHCommand(REBOOT_COMMAND);
                        sleep(param.getInterval());
                    }
                }
        ).start();
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
                add(TargetType.INSTANCE);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }
}
