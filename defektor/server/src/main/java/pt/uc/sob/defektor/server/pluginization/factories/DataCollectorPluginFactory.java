package pt.uc.sob.defektor.server.pluginization.factories;

import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.pluginization.AbstractPluginFactory;

import java.lang.reflect.InvocationTargetException;

public class DataCollectorPluginFactory extends AbstractPluginFactory {

    private static DataCollectorPluginFactory instance;

    protected DataCollectorPluginFactory() {
    }

    public static DataCollectorPluginFactory getInstance() {
        if(instance==null) instance = new DataCollectorPluginFactory();
        return instance;
    }

    @Override
    protected Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return clazz.getConstructor(SystemConfig.class).newInstance(objects);
    }
}
