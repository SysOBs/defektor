package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.Configuration;

import java.io.File;
import java.util.List;

public class SystemPluginInstaller extends AbstractPluginInstaller {
    protected static Configuration configuration = null;

    public SystemPluginInstaller(AbstractPluginFactory pluginFactory) {
        super(pluginFactory);
    }

    public static void init(Configuration configuration) {
        SystemPluginInstaller.configuration = configuration;
    }

    @Override
    protected String getPluginDirectory() {
        if(configuration == null) return null;
        return SystemPluginInstaller.configuration.getPluginFolder();
    }

    @Override
    protected List<PluginInfo> getPluginList(String type) {
        PluginManager pm = new PluginManager();
        return pm.getPluginList(type);
    }
}
