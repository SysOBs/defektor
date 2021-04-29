package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;

import java.util.List;

public class SystemTaskHandler {

    private SystemPlug plug = null;
    private SystemsManager systemsManager = new SystemsManager();

    public SystemTaskHandler(String command, AbstractSysConfig configuration) {
        plug = (SystemPlug) SystemPluginFactory.getInstance().getPluginInstance(command, configuration);
    }

    public SystemPlug getPlug() {
        return plug;
    }

    public List<TargetType> getTargetTypes() {
        if(plug == null) return null; //TODO exceptions
        return plug.getTargetTypes();
    }
}
