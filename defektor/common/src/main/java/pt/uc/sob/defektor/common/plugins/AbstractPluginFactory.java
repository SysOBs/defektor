package pt.uc.sob.defektor.common.plugins;

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

public abstract class AbstractPluginFactory {
    protected Map<String, Class> classMap = new ConcurrentHashMap<>();
    protected Map<String, URLClassLoader> classLoaderMap = new ConcurrentHashMap<>();
    protected String path;
    protected List<String> pluginNames;

    protected abstract Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    public Object getPluginInstance(String command, Object ...objects){
        Class clazz = classMap.get(command);
        Object object = null;
        if(clazz==null){
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

    protected abstract String getClassNameAttribute();

    public static String getCommandAttribute(){
        return "Command";
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

    public void loadPlugins(String path, List<String> pluginNames){
        this.path = path;
        this.pluginNames = pluginNames;
        File file;
        JarFile jar;
        Manifest man;
        String command, className;
        URL[] urls;
        URLClassLoader cl;
        Class clazz;
        classMap.clear();

        for (String name : pluginNames) {
            try {
                file = new File(path + File.separator + name + ".jar");
                System.out.println(file.getAbsolutePath());
                jar = new JarFile(file);
                man = jar.getManifest();
                command = man.getMainAttributes().getValue(getCommandAttribute());
                className = man.getMainAttributes().getValue(getClassNameAttribute());
                jar.close();
                urls = new URL[]{ new URL("jar:file:" + file.getPath() + "!/") };
                cl = URLClassLoader.newInstance(urls);
                classLoaderMap.put(command, cl);
                clazz = cl.loadClass(className);
                classMap.put(command, clazz);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("AbstractPluginFactory Exception");
                e.printStackTrace();
            }
        }
    }
}
