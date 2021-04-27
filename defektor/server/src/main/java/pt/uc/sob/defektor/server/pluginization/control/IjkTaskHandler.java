package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.server.pluginization.PluginFactory;

import java.util.List;

public class IjkTaskHandler {

    private InjektorPlug plug = null;
    private TaskManager task = new TaskManager();
    private InjektorsManager injektorsManager = new InjektorsManager();

    public IjkTaskHandler(String command) {
        plug = (InjektorPlug) PluginFactory.getInstance().getPluginInstance(command, injektorsManager, task);
    }

    public List<TargetType> getTargetTypes() {
        if(task == null) return null; //TODO exceptions
        if(plug == null) return null; //TODO exceptions
        return plug.getTargetTypes();
    }

    public List<Target> getTargetInstanceByType(TargetType targetType) {
        return null;
    }

    public void performInjection(AbstractParam param) {
        if(task == null) return; //TODO exceptions
        if(plug == null) return; //TODO exceptions
        plug.performInjection(param);
    }

    public void stopInjection() {
        if(task == null) return; //TODO exceptions
        if(plug == null) return; //TODO exceptions
        plug.stopInjection();
    }

    //TODO FAZER UM SSH REBOOT À MAQUINA
    // FAZER KILLALL A UM {NOME_PROCESSO}
    // FAZER KILL -9 {PID}

}
