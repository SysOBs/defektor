package pt.uc.sob.defektor.server.pluginization;


import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;

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
    protected List<String> getPluginList() {

//        return pluginManager.getPluginList(type);
        return null;
    }

}