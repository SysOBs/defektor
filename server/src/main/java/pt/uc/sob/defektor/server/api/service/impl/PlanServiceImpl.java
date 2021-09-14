package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.expection.InvalidPlanException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final DefektorRepository<PlanData> defektorRepository;

    @Override
    public PlanData planAdd(PlanData plan) throws DuplicateEntryException {
        plan.setId(Utils.generateUUID());
        defektorRepository.save(plan, Strings.DB.PLAN_DB_PATH);
        return plan;
    }

    @Override
    public PlanData planGet(UUID id) throws EntityNotFoundException {
        PlanData plan = defektorRepository.findById(id, Strings.DB.PLAN_DB_PATH);
        if(plan == null) throw new EntityNotFoundException(Strings.Errors.PLAN_NOT_FOUND);
        return plan;
    }

    @Override
    public List<PlanData> plansList() {
        return defektorRepository.findAll(Strings.DB.PLAN_DB_PATH);
    }

    @Override
    public void planValidate(PlanData plan) throws InvalidPlanException {
        //TODO To implement
    }

    @Override
    public void planDelete(UUID id) throws EntityNotFoundException {
        PlanData plan = defektorRepository.findById(id, Strings.DB.PLAN_DB_PATH);
        if(plan == null) throw new EntityNotFoundException(Strings.Errors.PLAN_NOT_FOUND);
        defektorRepository.delete(plan, Strings.DB.PLAN_DB_PATH);
    }

    @Override
    public void planDeleteAll() {
        defektorRepository.deleteAll(Strings.DB.PLAN_DB_PATH);
    }
}
