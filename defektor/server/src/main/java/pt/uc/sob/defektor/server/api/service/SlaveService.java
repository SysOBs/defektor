package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SlaveService {

    void slaveAdd(SlaveData slave) throws DuplicateEntryException;

    SlaveData slaveGet(UUID uuid) throws EntityNotFoundException;

    List<SlaveData> slavesList();

    void slaveDelete(UUID id) throws EntityNotFoundException;
}
