package pt.uc.sob.defektor.plugins.ijk.virtualmachine.instancereboot;

import lombok.SneakyThrows;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin_interface.InjektorPlug;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MachineShutdownIjkPlug extends InjektorPlug<VMSystemPlug> {

    private final String SHUTDOWN_COMMAND = "sudo shutdown";

    public MachineShutdownIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(Map<String, String> parameters) throws CampaignException {
//        Param param = Utils.jsonToObject(ijkParams.getJsonIjkParams().toString());
//        new Thread(
//                () -> {
//                    while (true) {
//                        system.sendSSHCommand(SHUTDOWN_COMMAND);
//                        sleep(param.getInterval());
//                    }
//                }
//        ).start();
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
                add(TargetType.);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }
}
