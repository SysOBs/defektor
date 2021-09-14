package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import pt.uc.sob.defektor.server.api.expection.InvalidSystemException;
import pt.uc.sob.defektor.server.campaign.Orchestrator;
import pt.uc.sob.defektor.server.api.PlanApi;
import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.expection.InvalidPlanException;
import pt.uc.sob.defektor.server.api.mapper.PlanMapper;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.model.Plan;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class PlanController implements PlanApi {

    private final Orchestrator orchestrator;
    private final PlanService planService;


    @Override
    public ResponseEntity<List<Plan>> planList() {
        return new ResponseEntity<>(
                planService.plansList().stream()
                        .map(PlanMapper::convertToDTO)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Plan> planAdd(@Valid Plan plan) {
        PlanData planData = PlanMapper.convertToDAO(plan);
        try {
            planService.planValidate(planData);
            planService.planAdd(planData);
            orchestrator.conductProcess(planData);
            return new ResponseEntity<>(plan, HttpStatus.CREATED);
        }
        catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        catch (DuplicateEntryException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        catch (InvalidPlanException | InvalidSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<Plan> planGet(UUID id) {
        try {
            return new ResponseEntity<>(
                    PlanMapper.convertToDTO(planService.planGet(id)),
                    HttpStatus.OK);
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
