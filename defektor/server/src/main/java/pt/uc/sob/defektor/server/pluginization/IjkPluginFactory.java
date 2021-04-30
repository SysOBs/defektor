package pt.uc.sob.defektor.server.pluginization;

import pt.uc.sob.defektor.common.SystemPlug;

import java.lang.reflect.InvocationTargetException;

public class IjkPluginFactory extends AbstractPluginFactory {

    private static IjkPluginFactory instance;

    protected IjkPluginFactory() {
    }

    public static IjkPluginFactory getInstance() {
        if (instance == null) instance = new IjkPluginFactory();
        return instance;
    }

    @Override
    protected Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return clazz.getConstructor(SystemPlug.class).newInstance(objects);
    }
}
