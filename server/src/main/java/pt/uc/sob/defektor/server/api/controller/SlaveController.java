package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.SlaveApi;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.model.Slave;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SlaveController implements SlaveApi {

    private final SlaveService slaveService;

    @Override
    public ResponseEntity<List<Slave>> slaveList() {
        List<Slave> slaveList = new ArrayList<>();
        for(SlaveData slaveData : slaveService.slavesList())
            slaveList.add(Mapper.convertToDTO(slaveData));

        return new ResponseEntity<>(slaveList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Slave> slaveAdd(@Valid Slave slave) {
        try {
            SlaveData slaveData = slaveService.slaveAdd(Mapper.convertToDAO(slave));
            Slave returnableSlave = Mapper.convertToDTO(slaveData);
            return new ResponseEntity<>(returnableSlave, HttpStatus.CREATED);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Slave> slaveGet(UUID id) {
        try {
            Slave returnableSlave = Mapper.convertToDTO(slaveService.slaveGet(id));
            return new ResponseEntity<>(returnableSlave, HttpStatus.OK);
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

    @Override
    public ResponseEntity<Void> slaveDeleteAll() {
        slaveService.slaveDeleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
