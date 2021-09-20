//package pt.uc.sob.defektor.server.api.controller;
//
//import org.json.JSONObject;
//import org.junit.Test;
//import pt.uc.sob.defektor.common.InjektorPlug;
//import pt.uc.sob.defektor.common.SystemConnectorPlug;
//import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
//
//import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
//
//public class PluginTest {
//
//    @Test
//    public void loadPlugins() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//
//        String DESKTOP_DIR = System.getProperty("user.home") + "/Desktop";
//
//        String vmPath = DESKTOP_DIR + "/defektor/defektor/plugins/libs/system/virtualmachine.jar";
//        File vmFile = new File(vmPath);
//        URL[] urls = new URL[]{ new URL("jar:file:" + vmFile.getPath() + "!/") };
//        ClassLoader vmClassLoader = URLClassLoader.newInstance(urls);
//        String vmClassName = "pt.uc.sob.defektor.plugins.system.virtualmachine.VMSystemPlug";
//        Class vmClass = vmClassLoader.loadClass(vmClassName);
//
//        JSONObject object = new JSONObject();
//        object.put("username", "goncalo");
//        object.put("host", "192.168.1.2");
//        object.put("port", 22);
//        object.put("privateKey", "~/.ssh/id_rsa");
//
//        SystemConfigs config = new SystemConfigs(object);
//        SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) vmClass.getConstructor(SystemConfigs.class).newInstance(config);
//
//
//        String ijkPath = DESKTOP_DIR + "/defektor/defektor/plugins/libs/ijk/instancereboot.jar";
//        File ijkFile = new File(ijkPath);
//        URL[] ijkUrls = new URL[]{ new URL("jar:file:" + ijkFile.getPath() + "!/") };
//        ClassLoader ijkClassLoader = URLClassLoader.newInstance(ijkUrls);
//        String className = "pt.uc.sob.defektor.plugins.ijk.instancereboot.InstanceRebootIjkPlug";
//        Class ijkClass = ijkClassLoader.loadClass(className);
//        InjektorPlug injektorPlug = (InjektorPlug) ijkClass.getConstructor(SystemConnectorPlug.class).newInstance(systemConnectorPlug);
//        System.out.println(injektorPlug.getTargetTypes());
//
//    }
//}
