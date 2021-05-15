package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.model.Plan;

import java.util.stream.Collectors;

public class PlanMapper {

    public static PlanData convertToDAO(Plan plan) {
        PlanData planData = new PlanData();
        planData.setId(plan.getId());
        planData.setName(plan.getName());
        planData.setInjektions(
                plan.getInjektions().stream()
                        .map(InjektionMapper::convertToDAO)
                        .collect(Collectors.toList())
        );

        return planData;
    }
    public static Plan convertToDTO(PlanData planData) {
        Plan plan = new Plan();
        plan.setId(planData.getId());
        plan.setName(planData.getName());
        //TODO FINISH SYSTEM DAO/DTO MAPPING
//        plan.setSystem();
        plan.setInjektions(
                planData.getInjektions().stream()
                        .map(InjektionMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return plan;
    }
}
