package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.IjkApi;
import pt.uc.sob.defektor.server.model.Ijk;
import pt.uc.sob.defektor.server.model.TargetType;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;
import pt.uc.sob.defektor.server.pluginization.control.IjkTaskHandler;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class IjkController implements IjkApi {

    @Override
    public ResponseEntity<List<Ijk>> ijkList() {
        System.out.println(IjkPluginFactory.getInstance().getLoadedPlugins());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
