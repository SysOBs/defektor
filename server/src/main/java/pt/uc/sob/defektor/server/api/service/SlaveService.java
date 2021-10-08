package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface SlaveService {

    SlaveData slaveAdd(SlaveData slave) throws DuplicateEntryException;

    SlaveData slaveGet(UUID uuid) throws EntityNotFoundException;

    List<SlaveData> slavesList();

    void slaveDelete(UUID id) throws EntityNotFoundException;

    void slaveDeleteAll();
}
