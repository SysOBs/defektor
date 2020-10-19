package pt.uc.sob.defektor.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.workloadgen.WorkloadGenerator;

import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class PlanController implements PlanApi {

    private static final String DESKTOP_DIR = "/home/goncalo/Desktop";

    @Override
    public ResponseEntity<List<Plan>> planList() {
        try {
            List<Plan> planList = new ArrayList<>(
                    Arrays.asList(
                            new ObjectMapper().readValue(new File(DESKTOP_DIR + "/plan.json"), Plan[].class)
                    )
            );
            return new ResponseEntity<>(planList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Plan> planAdd(@Valid Plan plan) {
        /*
            MISSING:
                - REFORMULATE PERSISTENCE (RIGHT NOW IS STORING IN A LOCAL FILE, AND IT IS OVERWRITTEN WHEN A NEW ONE IS ADDED)
         */

        String fileName = DESKTOP_DIR + "/plan.json";
        List<Plan> planList = Utils.serializePlansFromFile(fileName);
        if(planList == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(!Utils.isPlanUnique(planList, plan)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        planList.add(plan);
        if(Utils.writePlanListInFile(planList, fileName) == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        WorkloadGenerator.applyWorkload(
                plan.getId(), //PLAN ID
                plan.getInjektions().get(0).getWorkLoad().getNamespace(), //PLAN NAMESPACE
                Utils.stringListSplitter(plan.getInjektions().get(0).getWorkLoad().getEnv(), "="), //ENV VARIABLES
                plan.getInjektions().get(0).getWorkLoad().getReplicas() // NUM OF WORKLOAD REPLICAS
        );
        return new ResponseEntity<>(plan, HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<Plan> planGet(UUID planId) {
        /*
            FIX:
                - FILE IS OVERWRITING THE PREVIOUS PLAN
         */
        try {
            Plan plan = new ObjectMapper().readValue(new File("/home/goncalo/Desktop/plan.json"), Plan.class);
            if(plan.getId().equals(planId)){
                return new ResponseEntity<>(plan, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Void> planDelete(UUID planId) {
        String fileName = DESKTOP_DIR + "/plan.json";
        List<Plan> planList = Utils.serializePlansFromFile(fileName);
        if(planList == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        boolean planFound = false;
        for(Plan plan : planList){
            if(plan.getId().equals(planId)){
                planList.remove(plan);
                planFound = true;
                break;
            }
        }

        if(!planFound){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Utils.writePlanListInFile(planList, fileName);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Plan> planValidate(@Valid Plan plan) {
        return null;
    }
}
