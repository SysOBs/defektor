package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.SystemConfiguration;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.server.pluginization.PluginFactory;

import java.util.List;

public class SystemTaskHandler {

    private SystemPlug plug = null;
    private TaskManager task = new TaskManager();
    private SystemsManager systemsManager = new SystemsManager();

    public SystemTaskHandler(String command) {
        plug = (SystemPlug) PluginFactory.getInstance().getPluginInstance(command, systemsManager, task);
    }

    public SystemPlug getPlug() {
        return plug;
    }

    public void configure(SystemConfiguration configuration) {
        if(plug == null) return; //TODO exceptions
        plug.configure(configuration);

    }

    public List<TargetType> getTargetTypes() {
        if(task == null) return null; //TODO exceptions
        if(plug == null) return null; //TODO exceptions
        return plug.getTargetTypes();
    }

    public void setup() {
        if(task == null) return; //TODO exceptions
        if(plug == null) return; //TODO exceptions
        plug.setup();
    }

}
