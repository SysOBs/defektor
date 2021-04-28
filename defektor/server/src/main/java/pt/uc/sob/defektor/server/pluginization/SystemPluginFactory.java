package pt.uc.sob.defektor.server.pluginization;

import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;

import java.lang.reflect.InvocationTargetException;

public class SystemPluginFactory extends AbstractPluginFactory {

    private static SystemPluginFactory instance;

    protected SystemPluginFactory() {
    }

    public static SystemPluginFactory getInstance() {
        if(instance==null) instance = new SystemPluginFactory();
        return instance;
    }

    @Override
    protected Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return clazz.getConstructor(AbstractSysConfig.class).newInstance(objects);
    }

    @Override
    protected String getClassNameAttribute() {
            return "PluginClass";
    }

}
