package pt.uc.sob.defektor.server.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.uc.sob.defektor.common.com.PluginInfo;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.server.pluginization.control.PluginManager;
import pt.uc.sob.defektor.server.pluginization.control.TaskHandler;

import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class DemoPluginController {

    private PluginManager pluginManager = new PluginManager();

    @GetMapping("/plugin/list")
    public List<PluginInfo> getPluginList() {
        return pluginManager.getPluginList();
    }

    @PostMapping("/plugin/{command}/start")
    public void startInjection(@PathVariable("command") String command) {
        TaskHandler taskHandler = new TaskHandler(command);
        taskHandler.performInjection();
    }

    @GetMapping("/plugin/{command}/target/type")
    public List<TargetType> help(@PathVariable("command") String command) {
        TaskHandler taskHandler = new TaskHandler(command);
        return taskHandler.getTargetTypes();
    }
}