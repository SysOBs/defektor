package pt.uc.sob.defektor.server.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.TargetApi;
import pt.uc.sob.defektor.server.model.Target;
import pt.uc.sob.defektor.server.model.TargetType;

import java.util.List;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class TargetController implements TargetApi {

    @Override
    public ResponseEntity<List<Target>> targetGet(String target) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<TargetType>> targetList() {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
