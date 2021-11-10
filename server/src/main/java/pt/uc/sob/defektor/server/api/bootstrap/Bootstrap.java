package pt.uc.sob.defektor.server.api.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.control.PluginInstaller;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final String SYS_PLUGINS_DIRS = "plugins" + File.separator + "libs" + File.separator + "system connector";
    private final String IJK_PLUGINS_DIRS = "plugins" + File.separator + "libs" + File.separator + "injektor";
    private final String DATA_PLUGINS_DIRS = "plugins" + File.separator + "libs" + File.separator + "data collector";

    @Override
    public void run(String... args) {
        new PluginInstaller(SystemConnectorPluginFactory.getInstance(), SYS_PLUGINS_DIRS).installPlugins();
        new PluginInstaller(IjkPluginFactory.getInstance(), IJK_PLUGINS_DIRS).installPlugins();
        new PluginInstaller(DataCollectorPluginFactory.getInstance(), DATA_PLUGINS_DIRS).installPlugins();
    }
}
