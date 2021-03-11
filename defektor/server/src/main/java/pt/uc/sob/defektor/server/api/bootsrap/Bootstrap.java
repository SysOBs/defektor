package pt.uc.sob.defektor.server.api.bootsrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.pluginization.PluginFactory;
import pt.uc.sob.defektor.server.pluginization.ServerConfiguration;
import pt.uc.sob.defektor.server.pluginization.control.ServerPluginInstaller;

import java.io.File;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final PlanService planService;

    @Override
    public void run(String... args) throws Exception {
        ServerConfiguration configuration = new ServerConfiguration(
                "plugins" + File.separator + "libs"
        );

        ServerPluginInstaller.init(configuration);
        new ServerPluginInstaller(PluginFactory.getInstance()).installPlugins();//        Plan plan = new Plan();
//        UUID uuid = UUID.randomUUID();
//        UUID uuid = UUID.fromString("50839192-b3d5-4ddf-92ce-c4b62e200b44");

//        plan.setId(uuid);
//        plan.setName("test");
////        planService.planAdd(plan);
//        Thread.sleep(10000);
//        planService.planDelete(uuid);
    }
}
