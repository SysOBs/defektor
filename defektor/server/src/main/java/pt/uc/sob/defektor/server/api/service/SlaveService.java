package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SlaveService {

    Slave slaveAdd(Slave slave) throws IOException, DuplicateEntryException;

    Slave slaveGet(UUID uuid) throws IOException;

    List<Slave> slavesList() throws IOException;

    void slaveDelete(UUID id) throws IOException;
}
