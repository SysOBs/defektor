package pt.uc.sob.defektor.server.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.params.InstanceRebootParam;
import pt.uc.sob.defektor.common.com.params.ProcessTerminatorParam;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.common.com.sysconfigs.VMConfig;
import pt.uc.sob.defektor.server.pluginization.control.IjkTaskHandler;
import pt.uc.sob.defektor.server.pluginization.control.SystemTaskHandler;

@RestController
public class TestController {

    @GetMapping("/test")
    public void performInjection() {
        AbstractSysConfig config = new VMConfig("goncalo", "192.168.1.2", 22, "~/.ssh/id_rsa");
        SystemTaskHandler systemTaskHandler = new SystemTaskHandler("virtual-machine", config);
        SystemPlug systemPlug = systemTaskHandler.getPlug();

        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("instance-reboot", systemPlug);
        ijkTaskHandler.performInjection(new InstanceRebootParam());
        System.out.println("Performed injection");
    }
}
