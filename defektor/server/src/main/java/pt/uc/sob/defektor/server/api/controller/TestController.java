package pt.uc.sob.defektor.server.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uc.sob.defektor.common.com.params.InstanceRebootParam;
import pt.uc.sob.defektor.common.com.params.ProcessTerminatorParam;
import pt.uc.sob.defektor.server.pluginization.control.IjkTaskHandler;

@RestController
public class TestController {

    @GetMapping("/test/1")
    public void performInjection() {
        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator");
        ijkTaskHandler.performInjection(new ProcessTerminatorParam("find"));
        System.out.println("Performed injection");
    }

    @GetMapping("/test/2")
    public void performInjection1() {
        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator");
        ijkTaskHandler.performInjection(new ProcessTerminatorParam(9999));
        System.out.println("Performed injection");
    }

    @GetMapping("/test/3")
    public void performInjection2() {
        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator");
        ijkTaskHandler.performInjection(new InstanceRebootParam("gbaptist", "key"));
        System.out.println("Performed injection");
    }
}
