package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.SystemApi;
import pt.uc.sob.defektor.server.model.SystemTarget;
import pt.uc.sob.defektor.server.model.SystemType;
import pt.uc.sob.defektor.server.pluginization.PluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SystemController implements SystemApi {

    @Override
    public ResponseEntity<List<SystemTarget>> systemTypeList() {
//        PluginManager pluginManager = new PluginManager();
//        List<PluginInfo> pluginInfoList = pluginManager.getPluginList("SYSTEM");
//        List<SystemTarget> systemTargetList = new ArrayList<>()
        System.out.println(SystemPluginFactory.getInstance().getLoadedPlugins());




        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SystemType>> systemTypeConfigure(@Valid @RequestBody SystemType systemType) {
        System.out.println(systemType);
//        JsonNode jsonNodeTree = new ObjectMapper().readTree(systemType.getSystemConfig().getParams().get(0));
//        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
//        System.out.println(jsonAsYaml);
//        File file = new File("yaml.yaml");
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(jsonAsYaml);
//        fileWriter.close();
        return null;
    }
}
