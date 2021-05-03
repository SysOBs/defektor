package pt.uc.sob.defektor.server.api.controller;

import org.junit.Test;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginTest {

    @Test
    public void loadPlugins() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String DESKTOP_DIR = System.getProperty("user.home") + "/Desktop";

        String vmPath = DESKTOP_DIR + "/defektor/defektor/plugins/libs/system/virtualmachine.jar";
        File vmFile = new File(vmPath);
        URL[] urls = new URL[]{ new URL("jar:file:" + vmFile.getPath() + "!/") };
        ClassLoader vmClassLoader = URLClassLoader.newInstance(urls);
        String vmClassName = "pt.uc.sob.defektor.common.plugins.system.virtualmachine.VMSystemPlug";
        Class vmClass = vmClassLoader.loadClass(vmClassName);
        AbstractSysConfig config = new AbstractSysConfig("goncalo", "192.168.1.2", 22, "~/.ssh/id_rsa");
        SystemPlug systemPlug = (SystemPlug) vmClass.getConstructor(AbstractSysConfig.class).newInstance(config);


        String ijkPath = DESKTOP_DIR + "/defektor/defektor/plugins/libs/ijk/instancereboot.jar";
        File ijkFile = new File(ijkPath);
        URL[] ijkUrls = new URL[]{ new URL("jar:file:" + ijkFile.getPath() + "!/") };
        ClassLoader ijkClassLoader = URLClassLoader.newInstance(ijkUrls, vmClassLoader);
        String className = "pt.uc.sob.defektor.plugins.ijk.instancereboot.InstanceRebootIjkPlug";
        Class ijkClass = ijkClassLoader.loadClass(className);
        InjektorPlug injektorPlug = (InjektorPlug) ijkClass.getConstructor(SystemPlug.class).newInstance(systemPlug);
        System.out.println(injektorPlug.getTargetTypes());

    }
}
