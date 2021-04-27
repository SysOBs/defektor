package pt.uc.sob.defektor.server.api.bootsrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;
import pt.uc.sob.defektor.server.pluginization.control.PluginInstaller;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final String SYS_PLUGINS_DIRS = "plugins" + File.separator + "libs" + File.separator + "system";
    private final String IJK_PLUGINS_DIRS = "plugins" + File.separator + "libs" + File.separator + "ijk";

    @Override
    public void run(String... args) {
        //TODO doesn't work
        new PluginInstaller(SystemPluginFactory.getInstance(), SYS_PLUGINS_DIRS).installPlugins();
        new PluginInstaller(IjkPluginFactory.getInstance(), IJK_PLUGINS_DIRS).installPlugins();
    }
}
