package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import pt.uc.sob.defektor.server.api.PlanApi;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.model.Plan;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class PlanController implements PlanApi {

    private final PlanService planService;

    @Override
    public ResponseEntity<List<Plan>> planList() {
        List<Plan> planList = new ArrayList<>();
        for(PlanData planData : planService.plansList())
            planList.add(Mapper.convertToDTO(planData));

        return new ResponseEntity<>(planList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Plan> planAdd(@Valid Plan plan)  {
        PlanData planData = Mapper.convertToDAO(plan);
        try {
            planData = planService.planAdd(planData);
            Plan returnablePlan = Mapper.convertToDTO(planData);
            return new ResponseEntity<>(returnablePlan, HttpStatus.CREATED);
        } catch (InvalidSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<Plan> planGet(UUID id) {
        try {
            Plan returnablePlan = Mapper.convertToDTO(planService.planGet(id));
            return new ResponseEntity<>(returnablePlan, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> planDelete(UUID id) {
        try {
            planService.planDelete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Plan> planValidate(@Valid Plan plan) {
        return null;
    }

    @Override
    public ResponseEntity<Void> planDeleteAll(){
        planService.planDeleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
