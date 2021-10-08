package pt.uc.sob.defektor.server.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.PluginApi;
import pt.uc.sob.defektor.server.pluginization.factories.DataCollectorPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;

import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class PluginController implements PluginApi {

    @Override
    public ResponseEntity<List<String>> ijkList() {
        return new ResponseEntity<>(IjkPluginFactory.getInstance().getLoadedPlugins(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> sysConnectorList() {
        return new ResponseEntity<>(SystemConnectorPluginFactory.getInstance().getLoadedPlugins(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> dataCollectorList() {
        return new ResponseEntity<>(DataCollectorPluginFactory.getInstance().getLoadedPlugins(), HttpStatus.OK);
    }

}
