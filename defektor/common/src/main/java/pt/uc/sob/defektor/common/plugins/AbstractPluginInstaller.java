package pt.uc.sob.defektor.common.plugins;


import pt.uc.sob.defektor.common.com.info.InjektorInfo;
import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.common.com.info.SystemInfo;
import pt.uc.sob.defektor.common.utils.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPluginInstaller {
    protected AbstractPluginFactory pluginFactory;
    protected abstract String getPluginDirectory();
    protected abstract List<PluginInfo> getPluginList(String type);

    public AbstractPluginInstaller(AbstractPluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public void installPlugins() {

        final String pluginDir = getPluginDirectory();
        List<PluginInfo> plugins = new ArrayList<>();
        List<PluginInfo> ijkPlugins = null;
        List<PluginInfo> systemPlugins = null;
        List<String> pluginDirList = new ArrayList<>();

        pluginFactory.unload();

        //TODO Could be improved
        System.out.println("Ijk loaded plugins:");
        ijkPlugins = getPluginList("IJK");
        ijkPlugins.forEach(pluginInfo -> {
            System.out.println("\t - " + pluginInfo.getName());
        });

        System.out.println("System loaded plugins:");
        systemPlugins = getPluginList("SYSTEM");
        systemPlugins.forEach(pluginInfo -> {
            System.out.println("\t - " + pluginInfo.getName());
        });


        plugins.addAll(ijkPlugins);
        plugins.addAll(systemPlugins);

        List<String> pluginNames = new LinkedList<>();

        for (PluginInfo pi : plugins) {
            if(pi instanceof InjektorInfo)
                pluginDirList.add(pluginDir + File.separator + "ijk");
            else if (pi instanceof SystemInfo)
                pluginDirList.add(pluginDir + File.separator + "system");
            pluginNames.add(pi.getName());
        }

        pluginFactory.loadPlugins(pluginDirList, pluginNames);
    }
}
