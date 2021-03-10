package pt.uc.sob.defektor.server.api.repository;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;

import java.util.List;
import java.util.UUID;

public interface DefektorRepository<T> {

    void save(T t, String dbFileDir) throws DuplicateEntryException;

    T findById(UUID id, String dbFileDir);

    void delete(T plan, String dbFileDir);

    List<T> findAll(String dbFileDir);
}
