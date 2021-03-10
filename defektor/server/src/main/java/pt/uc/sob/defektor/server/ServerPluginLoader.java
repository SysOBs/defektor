package pt.uc.sob.defektor.server;


import pt.uc.sob.defektor.common.com.PluginInfo;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;
import pt.uc.sob.defektor.server.control.PluginManager;

import java.util.List;

public class ServerPluginLoader extends AbstractPluginInstaller {

    public ServerPluginLoader(AbstractPluginFactory pluginFactory) {
        super(pluginFactory);
    }

    @Override
    protected String getPluginDirectory() {
        return "libs";
    }

    @Override
    protected List<PluginInfo> getPluginList() {
        PluginManager pluginManager = new PluginManager();
        return pluginManager.getPluginList();
    }
}