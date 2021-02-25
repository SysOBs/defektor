package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.repository.PlanRepository;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public Plan planAdd(Plan plan) throws IOException, DuplicateEntryException {
        return planRepository.save(plan);
    }

    @Override
    public Plan planGet(UUID id) throws IOException {
        return planRepository.findById(id);
    }

    @Override
    public List<Plan> plansList() throws IOException {
        return planRepository.findAll();
    }

    @Override
    public void planValidate(Plan plan) {
        //TODO To implement
    }

    @Override
    public void planDelete(UUID id) throws IOException, NullPointerException {
        Plan plan = planRepository.findById(id);
        if(plan == null) {
            throw new NullPointerException();
        }
        planRepository.delete(plan);
    }
}
