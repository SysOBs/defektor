package pt.uc.sob.defektor.server;

import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.lang.reflect.InvocationTargetException;

public class PluginFactory extends AbstractPluginFactory {
	private static PluginFactory instance;

	private PluginFactory() {
	}

	public static PluginFactory getInstance() {
		if(instance==null) instance = new PluginFactory();
		return instance;
	}

	@Override
	protected Object instantiate(Class<?> clazz, Object ...objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return clazz.getConstructor(InjektorsManagerInterface.class, TaskManagerInterface.class).newInstance(objects);
	}


	@Override
	protected String getClassNameAttribute() {
		return "pt.uc.sob.defektor.common.ServerPlug";
	}

}
