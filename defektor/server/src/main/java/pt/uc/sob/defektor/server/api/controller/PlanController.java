package pt.uc.sob.defektor.server.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.Orchestrator;
import pt.uc.sob.defektor.server.api.PlanApi;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.service.PlanService;
import pt.uc.sob.defektor.server.model.Plan;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
@RequiredArgsConstructor
public class PlanController implements PlanApi {

    private Orchestrator orchestrator;
    private final PlanService planService;


    @Override
    public ResponseEntity<List<Plan>> planList() {
        try {
            return new ResponseEntity<>(planService.plansList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Plan> planAdd(@Valid Plan plan) {
        try {
            planService.planAdd(plan);
            //TODO START PROCESS
//            this.orchestrator.conductProcess(plan);
            return new ResponseEntity<>(plan, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (DuplicateEntryException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<Plan> planGet(UUID id) {
        try {
            Plan plan = planService.planGet(id);

            if(plan == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(plan, HttpStatus.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> planDelete(UUID id) {
        try {
            planService.planDelete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Plan> planValidate(@Valid Plan plan) {
        return null;
    }
}
