package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PlanService {

    Plan planAdd(Plan plan) throws IOException, DuplicateEntryException;

    Plan planGet(UUID uuid) throws IOException;

    List<Plan> plansList() throws IOException;

    void planValidate(Plan plan);

    void planDelete(UUID id) throws IOException;
}
