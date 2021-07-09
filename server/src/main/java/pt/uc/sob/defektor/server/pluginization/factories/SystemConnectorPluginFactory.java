package pt.uc.sob.defektor.server.pluginization.factories;

import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.pluginization.AbstractPluginFactory;

import java.lang.reflect.InvocationTargetException;

public class SystemConnectorPluginFactory extends AbstractPluginFactory {

    private static SystemConnectorPluginFactory instance;

    protected SystemConnectorPluginFactory() {
    }

    public static SystemConnectorPluginFactory getInstance() {
        if(instance==null) instance = new SystemConnectorPluginFactory();
        return instance;
    }

    @Override
    protected Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return clazz.getConstructor(SystemConfig.class).newInstance(objects);
    }
}
