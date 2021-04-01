package pt.uc.sob.defektor.server.pluginization;


import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.control.PluginManager;

import java.io.File;
import java.util.List;

public class ServerPluginLoader extends AbstractPluginInstaller {

    public ServerPluginLoader(AbstractPluginFactory pluginFactory) {
        super(pluginFactory);
    }

    @Override
    protected String getPluginDirectory() {
        return "plugins" + File.separator + "libs" + File.separator;
    }

    @Override
    protected List<PluginInfo> getPluginList(String type) {
        PluginManager pluginManager = new PluginManager();
        return pluginManager.getPluginList(type);
    }
}