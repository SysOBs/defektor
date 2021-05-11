package pt.uc.sob.defektor.server.pluginization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AbstractPluginFactory {
    protected Map<String, Class> classMap = new ConcurrentHashMap<>();
    protected Map<String, URLClassLoader> classLoaderMap = new ConcurrentHashMap<>();
    protected List<String> pluginPaths;

    protected abstract Object instantiate(Class<?> clazz, Object... objects) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    public Object getPluginInstance(String pluginName, Object ...objects){
        //TODO CHANGE "Command" to "PluginName"
        Class clazz = classMap.get(pluginName);
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

    public Map<String, URLClassLoader> getClassLoaderMap(){
        return classLoaderMap;
    }

    public void setClassLoaderMap(Map<String, URLClassLoader> classLoaderMap){
        this.classLoaderMap.putAll(classLoaderMap);
    }

    public List<String> getLoadedPlugins() {
        return classLoaderMap.keySet().stream().map(Object::toString).collect(Collectors.toList());
    }

    public abstract void loadPlugins(List<String> pluginPaths);
}
