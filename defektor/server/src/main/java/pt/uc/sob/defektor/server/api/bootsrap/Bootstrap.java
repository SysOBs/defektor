package pt.uc.sob.defektor.server.api.bootsrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.uc.sob.defektor.server.pluginization.PluginFactory;
import pt.uc.sob.defektor.server.pluginization.Configuration;
import pt.uc.sob.defektor.server.pluginization.control.IjkPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.control.ServerPluginInstaller;
import pt.uc.sob.defektor.server.pluginization.control.SystemPluginInstaller;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final String BASE_DIR = "plugins" + File.separator + "libs" + File.separator;
    @Override
    public void run(String... args) {
        Configuration configuration = new Configuration(BASE_DIR);

        ServerPluginInstaller.init(configuration);
        new ServerPluginInstaller(PluginFactory.getInstance()).installPlugins();
    }
}
