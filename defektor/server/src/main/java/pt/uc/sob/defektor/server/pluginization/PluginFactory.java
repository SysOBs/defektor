package pt.uc.sob.defektor.server.pluginization;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;

import java.lang.reflect.InvocationTargetException;

public class PluginFactory extends AbstractPluginFactory {
	private static PluginFactory instance;

	protected PluginFactory() {
	}

	public static PluginFactory getInstance() {
		if(instance==null) instance = new PluginFactory();
		return instance;
	}

	@Override
	protected Object instantiate(Class<?> clazz, Object ...objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if(SystemPlug.class.isAssignableFrom(clazz))
			return clazz.getConstructor(AbstractSysConfig.class).newInstance(objects);
		else if (InjektorPlug.class.isAssignableFrom(clazz))
			return clazz.getConstructor(SystemPlug.class).newInstance(objects);
		else
			return null;
	}


	@Override
	protected String getClassNameAttribute() {
		return "PluginClass";
	}

}
