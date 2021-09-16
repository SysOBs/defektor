package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.expection.InvalidPlanException;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PlanService {

    PlanData planAdd(PlanData plan) throws DuplicateEntryException, InvalidSystemException;

    PlanData planGet(UUID uuid) throws EntityNotFoundException;

    List<PlanData> plansList();

    void planValidate(PlanData plan) throws InvalidPlanException;

    void planDelete(UUID id) throws EntityNotFoundException;

    void planDeleteAll();
}
