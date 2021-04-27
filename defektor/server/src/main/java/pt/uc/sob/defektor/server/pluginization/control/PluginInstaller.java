package pt.uc.sob.defektor.server.pluginization.control;


import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import pt.uc.sob.defektor.common.plugins.AbstractPluginFactory;
import pt.uc.sob.defektor.common.plugins.AbstractPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.Configuration;

import java.util.List;

public class PluginInstaller extends AbstractPluginInstaller {

    private final String pluginPath;

    public PluginInstaller(AbstractPluginFactory pluginFactory, String pluginPath) {
        super(pluginFactory);
        this.pluginPath = pluginPath;
    }

    @Override
    protected String getPluginDirectory() {
        return pluginPath;
    }
}
