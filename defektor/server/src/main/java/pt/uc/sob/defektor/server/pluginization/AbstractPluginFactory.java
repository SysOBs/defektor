package pt.uc.sob.defektor.server.pluginization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public abstract class AbstractPluginFactory {
    protected Map<String, Class> classMap = new ConcurrentHashMap<>();
    protected Map<String, URLClassLoader> classLoaderMap = new ConcurrentHashMap<>();
    protected List<String> pluginPaths;

    protected abstract Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    public Object getPluginInstance(String command, Object ...objects){
        Class clazz = classMap.get(command);
        Object object = null;
        if(clazz==null){
            //TODO THROW EXCEPTION
            System.out.println("NO CLASS :D");
            return null;
        }

        try {
            object = instantiate(clazz, objects);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("AbstractPluginFactory Exception");
            e.printStackTrace();
        }
        return object;
    }

    public void unload(){
        classLoaderMap.values().forEach(
                classLoader -> {
                    try {
                        classLoader.close();
                    } catch (IOException ignore) {}
                }
        );
        classLoaderMap.clear();
    }

    public List<String> getLoadedPlugins() {
        return classLoaderMap.keySet().stream().map(Object::toString).collect(Collectors.toList());
    }

    public void loadPlugins(List<String> pluginPaths){
        this.pluginPaths = pluginPaths;
        File file;
        JarFile jar;
        Manifest man;
        String command, className;
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
                command = man.getMainAttributes().getValue("Command");
                className = man.getMainAttributes().getValue("PluginClass");
                jar.close();
                urls = new URL[]{ new URL("jar:file:" + file.getPath() + "!/") };
                cl = URLClassLoader.newInstance(urls);
                classLoaderMap.put(command, cl);
                clazz = cl.loadClass(className);
                classMap.put(command, clazz);
            } catch (IOException | ClassNotFoundException e) {
                //TODO EXCEPTION
                System.out.println("AbstractPluginFactory Exception");
                e.printStackTrace();
            }
        }
    }
}
