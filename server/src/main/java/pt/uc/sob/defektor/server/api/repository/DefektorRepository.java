package pt.uc.sob.defektor.server.api.repository;

import java.util.List;
import java.util.UUID;

public interface DefektorRepository<T> {

    void save(T t, String dbFilePath);

    void update(T t, String dbFilePath);

    T findById(UUID id, String dbFilePath);

    void delete(T plan, String dbFilePath);

    void deleteAll(String dbFilePath);

    List<T> findAll(String dbFilePath);
}
