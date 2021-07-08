package pt.uc.sob.defektor.server.api.repository;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;

import java.util.List;
import java.util.UUID;

public interface DefektorRepository<T> {

    void save(T t, String dbFilePath) throws DuplicateEntryException;

    T findById(UUID id, String dbFilePath);

    void delete(T plan, String dbFilePath);

    void deleteAll(String dbFilePath);

    List<T> findAll(String dbFilePath);
}
