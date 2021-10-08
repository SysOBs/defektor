package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;

import java.util.List;
import java.util.UUID;

public interface PlanService {

    PlanData planAdd(PlanData plan) throws InvalidSystemException;

    PlanData planGet(UUID uuid) throws EntityNotFoundException;

    List<PlanData> plansList();

    void planValidate(PlanData plan);

    void planDelete(UUID id) throws EntityNotFoundException;

    void planDeleteAll();
}
