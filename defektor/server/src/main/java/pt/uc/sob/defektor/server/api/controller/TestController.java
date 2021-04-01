package pt.uc.sob.defektor.server.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uc.sob.defektor.server.pluginization.control.IjkTaskHandler;

@RestController
public class TestController {

    @GetMapping("/test")
    public void performInjection() {
        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator");
        ijkTaskHandler.performInjection();
        System.out.println("Performed injection");
    }
}
