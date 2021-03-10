package pt.uc.sob.defektor.common.plugins;


import pt.uc.sob.defektor.common.com.PluginInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPluginInstaller {
    protected AbstractPluginFactory pluginFactory;
    protected abstract String getPluginDirectory();
    protected abstract List<PluginInfo> getPluginList();


    public AbstractPluginInstaller(AbstractPluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public void installPlugins() {
        final String pluginDir = getPluginDirectory();
        System.out.println(pluginDir);
        List<PluginInfo> plugins = null;

        pluginFactory.unload();

        while (plugins == null) {
            plugins = getPluginList();
        }

        List<String> pluginNames = new LinkedList<>();

        for (PluginInfo pi : plugins) {
            System.out.println(pi.getName());
            File file = new File(pluginDir + File.separator + pi.getName() + ".jar");

//            file.getParentFile().mkdirs();
//            file.delete();

//            while (!file.exists()) {
//                try {
//                    FileUtils.copyURLToFile(new URL(pi.getUrl()), file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    file.delete();
//                }
//            }
            pluginNames.add(pi.getName());
        }
        pluginFactory.loadPlugins(getPluginDirectory(), pluginNames);
    }
}
