package pt.uc.sob.defektor.server.pluginization;

import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.server.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static pt.uc.sob.defektor.server.utils.Strings.KUBERNETES;
import static pt.uc.sob.defektor.server.utils.Strings.VIRTUAL_MACHINE;


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

    @Override
    public void loadPlugins(List<String> pluginPaths) {
        this.pluginPaths = pluginPaths;
        File file;
        JarFile jar;
        Manifest man;
        String pluginName, pluginClassName;
        URL[] urls;
        URLClassLoader cl;
        Class clazz;
        classMap.clear();

        for (String path : pluginPaths) {
            //TODO Merge all URLs in the same ClassLoader
            try {
                file = new File(path);
                jar = new JarFile(file);
                man = jar.getManifest();
                pluginName = man.getMainAttributes().getValue("PluginName");
                pluginClassName = man.getMainAttributes().getValue("PluginClass");
                jar.close();
                urls = new URL[]{ new URL("jar:file:" + file.getPath() + "!/") };

                Map<String, URLClassLoader> systemsClassLoaders = SystemPluginFactory.getInstance().getClassLoaderMap();
                String systemName;
                if(pluginClassName.contains(VIRTUAL_MACHINE))
                    systemName = VIRTUAL_MACHINE;
                else if (pluginClassName.contains(KUBERNETES))
                    systemName = KUBERNETES;
                else {
                    throw new RuntimeException("Couldn't find the matching system");
                }

                ClassLoader parentClassLoader = systemsClassLoaders.get(systemName);

                cl = URLClassLoader.newInstance(urls, parentClassLoader);
                classLoaderMap.put(pluginName, cl);
                clazz = cl.loadClass(pluginClassName);
                classMap.put(pluginName, clazz);
            } catch (IOException | ClassNotFoundException e) {
                //TODO EXCEPTION
                System.out.println("AbstractPluginFactory Exception");
                e.printStackTrace();
            }
        }
    }
}
