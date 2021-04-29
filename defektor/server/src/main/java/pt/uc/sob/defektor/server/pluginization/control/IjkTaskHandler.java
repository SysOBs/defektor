package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;

import java.util.List;

public class IjkTaskHandler {

    private InjektorPlug plug = null;
    private InjektorsManager injektorsManager = new InjektorsManager();

    public IjkTaskHandler(String command, SystemPlug systemPlug) {
        plug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(command, systemPlug);
    }

    public List<TargetType> getTargetTypes() {
        if(plug == null) return null; //TODO exceptions
        return plug.getTargetTypes();
    }

    public List<Target> getTargetInstanceByType(TargetType targetType) {
        return null;
    }

    public void performInjection(AbstractParam param) {
        if(plug == null) return; //TODO exceptions
        plug.performInjection(param);
    }

    public void stopInjection() {
        if(plug == null) return; //TODO exceptions
        plug.stopInjection();
    }
}
