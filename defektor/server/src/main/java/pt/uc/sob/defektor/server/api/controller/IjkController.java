package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.server.api.IjkApi;
import pt.uc.sob.defektor.server.model.Ijk;
import pt.uc.sob.defektor.server.model.TargetType;
import pt.uc.sob.defektor.server.pluginization.control.IjkTaskHandler;
import pt.uc.sob.defektor.server.pluginization.control.PluginManager;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class IjkController implements IjkApi {

    @Override
    public ResponseEntity<List<Ijk>> ijkList() {
        PluginManager pluginManager = new PluginManager();
        List<PluginInfo> pluginInfoList = pluginManager.getPluginList("IJK");
        List<Ijk> ijkList = new ArrayList<>();

        for (PluginInfo pluginInfo : pluginInfoList ) {
            IjkTaskHandler ijkTaskHandler = new IjkTaskHandler(pluginInfo.getName());
            Ijk ijk = new Ijk();
            ijk.setName(pluginInfo.getName());

            // TODO DO BETTER
            List<TargetType> b = new ArrayList<>();
            for (pt.uc.sob.defektor.common.com.TargetType a : ijkTaskHandler.getTargetTypes()) {
                TargetType targetType = new TargetType();
                targetType.setName(a.name());
                b.add(targetType);
            }
            ijk.setTargetTypes(b);
            ijkList.add(ijk);
        }
        //TODO Do all the logic behind this command
        return new ResponseEntity<>(ijkList, HttpStatus.OK);
    }
}
