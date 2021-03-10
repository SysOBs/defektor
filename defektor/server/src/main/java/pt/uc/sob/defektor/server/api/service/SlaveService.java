package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SlaveService {

    Slave slaveAdd(Slave slave) throws DuplicateEntryException;

    Slave slaveGet(UUID uuid) throws EntityNotFoundException;

    List<Slave> slavesList();

    void slaveDelete(UUID id) throws EntityNotFoundException;
}
