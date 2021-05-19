package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.Orchestrator;
import pt.uc.sob.defektor.server.api.SlaveApi;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.mapper.PlanMapper;
import pt.uc.sob.defektor.server.api.mapper.SlaveMapper;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.model.Slave;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SlaveController implements SlaveApi {

    private final SlaveService slaveService;

    @Override
    public ResponseEntity<List<Slave>> slaveList() {
        return new ResponseEntity<>(
                slaveService.slavesList().stream()
                        .map(SlaveMapper::convertToDTO)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Slave> slaveAdd(@Valid Slave slave) {
        try {
            slaveService.slaveAdd(SlaveMapper.convertToDAO(slave));
            return new ResponseEntity<>(slave, HttpStatus.CREATED);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Slave> slaveGet(UUID id) {
        try {
            return new ResponseEntity<>(SlaveMapper.convertToDTO(slaveService.slaveGet(id)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> slaveDelete(UUID id) {
        try {
            slaveService.slaveDelete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
