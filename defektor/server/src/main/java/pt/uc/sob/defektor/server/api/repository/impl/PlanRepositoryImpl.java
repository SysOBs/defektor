package pt.uc.sob.defektor.server.api.repository.impl;

import org.springframework.stereotype.Repository;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.repository.PlanRepository;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Plan;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Repository
public class PlanRepositoryImpl implements PlanRepository {
    private final String PLAN_DB_FILE = "DefektorState\\plan.json";

    @Override
    public Plan save(Plan plan) throws IOException, DuplicateEntryException {
        List<Plan> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, Plan[].class);
        if(Utils.PlanUtils.isDuplicate(planList, plan))
            throw new DuplicateEntryException("teste");

        planList.add(plan);
        Utils.Json.writeJsonToFile(planList, PLAN_DB_FILE);
        return plan;
    }

    @Override
    public Plan findById(UUID id) throws IOException {
        List<Plan> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, Plan[].class);
        for(Plan plan : planList)
            if(plan.getId().equals(id))
                return plan;
        return null;
    }

    @Override
    public void delete(Plan plan) throws IOException,NullPointerException  {
        List<Plan> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, Plan[].class);

        planList.remove(plan);
        Utils.Json.writeJsonToFile(planList, PLAN_DB_FILE);
    }

    @Override
    public List<Plan> findAll() throws IOException {
        return Utils.Json.readJsonFromFile(PLAN_DB_FILE, Plan[].class);
    }
}
