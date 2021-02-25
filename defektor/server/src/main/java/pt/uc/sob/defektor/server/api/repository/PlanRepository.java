package pt.uc.sob.defektor.server.api.repository;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PlanRepository {
    Plan save(Plan plan) throws IOException, DuplicateEntryException;

    Plan findById(UUID id) throws IOException;

    void delete(Plan plan) throws IOException, NullPointerException;

    List<Plan> findAll() throws IOException;
}
