package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Plan;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final DefektorRepository defektorRepository;
    private final String PLAN_DB_FILE_DIR = Utils.getPlanFileDB();

    @Override
    public Plan planAdd(Plan plan) throws DuplicateEntryException {
        defektorRepository.save(Mapper.convertToDAO(plan), PLAN_DB_FILE_DIR);
        return plan;
    }

    @Override
    public Plan planGet(UUID id) {
        PlanData plan = (PlanData) defektorRepository.findById(id, PLAN_DB_FILE_DIR);

        if(plan == null)
            return null;

        return Mapper.convertToDTO(plan);
    }

    @Override
    public List<Plan> plansList() {
        return (List<Plan>) defektorRepository.findAll(PLAN_DB_FILE_DIR).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public void planValidate(Plan plan) {
        //TODO To implement
    }

    @Override
    public void planDelete(UUID id) throws NullPointerException {
        PlanData plan = (PlanData) defektorRepository.findById(id, PLAN_DB_FILE_DIR);
        if(plan == null) {
            throw new NullPointerException();
        }
        defektorRepository.delete(plan, PLAN_DB_FILE_DIR);
    }
}
