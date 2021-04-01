package pt.uc.sob.defektor.server.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.server.api.SystemApi;
import pt.uc.sob.defektor.server.model.SystemTarget;
import pt.uc.sob.defektor.server.model.SystemType;
import pt.uc.sob.defektor.server.model.TargetType;
import pt.uc.sob.defektor.server.pluginization.control.PluginManager;
import pt.uc.sob.defektor.server.pluginization.control.SystemTaskHandler;

import javax.validation.Valid;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SystemController implements SystemApi {

    @Override
    public ResponseEntity<List<SystemTarget>> systemTypeList() {
        PluginManager pluginManager = new PluginManager();
        List<PluginInfo> pluginInfoList = pluginManager.getPluginList("SYSTEM");
        List<SystemTarget> systemTargetList = new ArrayList<>();

        for (PluginInfo pluginInfo : pluginInfoList ) {
            SystemTaskHandler systemTaskHandler = new SystemTaskHandler(pluginInfo.getName());
            SystemTarget systemTarget = new SystemTarget();
            systemTarget.setName(pluginInfo.getName());
            systemTaskHandler.setup();

            // TODO BETTER
            List<TargetType> b = new ArrayList<>();
            for (pt.uc.sob.defektor.common.com.TargetType a : systemTaskHandler.getTargetTypes()) {
                TargetType targetType = new TargetType();
                targetType.setName(a.name());
                b.add(targetType);
            }
            systemTarget.setTargetTypes(b);
            systemTargetList.add(systemTarget);
        }
        return new ResponseEntity<>(systemTargetList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SystemType>> systemTypeConfigure(@Valid @RequestBody SystemType systemType) throws IOException {
        System.out.println(systemType);
        JsonNode jsonNodeTree = new ObjectMapper().readTree(systemType.getSystemConfig().getParams().get(0));
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        System.out.println(jsonAsYaml);
        File file = new File("yaml.yaml");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonAsYaml);
        fileWriter.close();
        return null;
    }
}
