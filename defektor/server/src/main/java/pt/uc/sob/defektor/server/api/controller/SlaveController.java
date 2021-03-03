package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.Orchestrator;
import pt.uc.sob.defektor.server.api.SlaveApi;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.model.Slave;
import sun.tools.asm.TryData;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class SlaveController implements SlaveApi {

    private Orchestrator orchestrator;
    private final SlaveService slaveService;

    @Override
    public ResponseEntity<List<Slave>> slaveList() {
        try {
            return new ResponseEntity<>(slaveService.slavesList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Slave> slaveAdd(@Valid Slave slave) {
        try {
            slaveService.slaveAdd(slave);
            return new ResponseEntity<>(slave, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (DuplicateEntryException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Slave> slaveGet(UUID id) {
        try {
            Slave slave = slaveService.slaveGet(id);

            if(slave == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(slave, HttpStatus.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> slaveDelete(UUID id) {
        try {
            slaveService.slaveDelete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
