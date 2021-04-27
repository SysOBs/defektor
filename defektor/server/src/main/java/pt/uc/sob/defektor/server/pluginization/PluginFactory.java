package pt.uc.sob.defektor.server.pluginization;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.SystemConfiguration;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.SystemsManagerInterface;

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
			return clazz.getConstructor(SystemConfiguration.class).newInstance(objects);
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
