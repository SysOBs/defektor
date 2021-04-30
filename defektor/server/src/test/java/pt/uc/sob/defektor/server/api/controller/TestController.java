package pt.uc.sob.defektor.server.api.controller;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.params.ProcessTerminatorParam;
import pt.uc.sob.defektor.common.com.sysconfigs.SysConfigInterface;
import pt.uc.sob.defektor.common.com.sysconfigs.VMConfig;
import pt.uc.sob.defektor.server.Server;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;

public class TestController {

    @Test
    public void performInjection() {
        new SpringApplication(Server.class).run(new String[]{});

        SysConfigInterface config = new VMConfig("goncalo", "192.168.1.2", 22, "~/.ssh/id_rsa");
        SystemPlug systemPlug = (SystemPlug) SystemPluginFactory.getInstance().getPluginInstance("virtual-machine", config);
        systemPlug.help();


        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("instance-reboot", systemPlug);
        System.out.println("Performed injection");
    }

    @Test
    public void performInjection1() {
        //TODO Mudar as config classes para dentro dos plugins
//        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator", systemPlug);
    }
}
