package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.SystemApi;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.mapper.PlanMapper;
import pt.uc.sob.defektor.server.api.mapper.SystemConfigMapper;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.model.SystemConfig;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SystemController implements SystemApi {

    private final SystemService systemService;

    @Override
    public ResponseEntity<List<SystemConfig>> systemConfigList() {
        return new ResponseEntity<>(
                systemService.sysConfigList().stream()
                        .map(SystemConfigMapper::convertToDTO)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SystemConfig> systemTypeConfigure(@Valid @RequestBody SystemConfig systemConfig) {
        try {
            systemService.sysConfigAdd(SystemConfigMapper.convertToDAO(systemConfig));
            return new ResponseEntity<>(systemConfig, HttpStatus.CREATED);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
