package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.Configuration;

import java.util.List;

public class ServerPluginInstaller extends AbstractPluginInstaller {
    protected static Configuration configuration = null;

    public ServerPluginInstaller(AbstractPluginFactory pluginFactory) {
        super(pluginFactory);
    }

    public static void init(Configuration configuration) {
        ServerPluginInstaller.configuration = configuration;
    }

    @Override
    protected String getPluginDirectory() {
        if(configuration == null) return null;
        return ServerPluginInstaller.configuration.getPluginFolder();
    }

    @Override
    protected List<PluginInfo> getPluginList(String type) {
        PluginManager pm = new PluginManager();
        return pm.getPluginList(type);
    }
}
