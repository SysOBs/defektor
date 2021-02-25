package pt.uc.sob.defektor.server.api.repository;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SlaveRepository {
    Slave save(Slave Slave) throws IOException, DuplicateEntryException;

    Slave findById(UUID id) throws IOException;

    void delete(Slave Slave) throws IOException, NullPointerException;

    List<Slave> findAll() throws IOException;
}
