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
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;
import pt.uc.sob.defektor.server.model.Plan;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final DefektorRepository defektorRepository;
    private final String plandbFilePath = Strings.PLAN_DB_PATH;

    @Override
    public Plan planAdd(Plan plan) throws DuplicateEntryException {
        defektorRepository.save(Mapper.convertToDAO(plan), plandbFilePath);
        return plan;
    }

    @Override
    public Plan planGet(UUID id) throws EntityNotFoundException {
        PlanData plan = (PlanData) defektorRepository.findById(id, plandbFilePath);

        if(plan == null)
            //TODO test
            throw new EntityNotFoundException("");

        return Mapper.convertToDTO(plan);
    }

    @Override
    public List<Plan> plansList() {
        return (List<Plan>) defektorRepository.findAll(plandbFilePath).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void planValidate(Plan plan) throws InvalidPlanException {
        //TODO To implement
    }

    @Override
    public void planDelete(UUID id) throws EntityNotFoundException {
        PlanData plan = (PlanData) defektorRepository.findById(id, plandbFilePath);
        if(plan == null) {
            throw new EntityNotFoundException("");
        }
        defektorRepository.delete(plan, plandbFilePath);
    }
}
