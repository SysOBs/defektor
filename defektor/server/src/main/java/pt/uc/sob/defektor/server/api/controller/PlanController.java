package pt.uc.sob.defektor.server.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.Orchestrator;
import pt.uc.sob.defektor.server.api.PlanApi;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.WorkloadGenerator;

import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class PlanController implements PlanApi {

    @Autowired
    private Orchestrator orchestrator;

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
        List<Plan> planList;

        planList = Utils.serializePlansFromFile(fileName);
        if(planList == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(planList.size() > 0 && !Utils.isPlanUnique(planList, plan)) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        planList.add(plan);
        if(Utils.writePlanListInFile(planList, fileName) == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        this.orchestrator.conductProcess(plan);
        /*
            FIX:
                - IT CAN ONLY HANDLE 1 INJECTION PER PLAN
        */



        return new ResponseEntity<>(plan, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Plan> planGet(UUID planId) {
        try {
            List<Plan> planList = new ArrayList<>(
                    Arrays.asList(
                            new ObjectMapper().readValue(new File(DESKTOP_DIR + "/plan.json"), Plan[].class)
                    )
            );

            for(Plan plan : planList){
                if(planId.equals(plan.getId())){
                    return new ResponseEntity<>(plan, HttpStatus.OK);
                }
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

        Plan selectedPlan = null;
        boolean planFound = false;
        for(Plan plan : planList){
            if(plan.getId().equals(planId)){
                selectedPlan = plan;
                planList.remove(plan);
                planFound = true;
                break;
            }
        }

        if(!planFound){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Utils.writePlanListInFile(planList, fileName);
//        WorkloadGenerator.stopWorkLoadGenerator(
//                selectedPlan.getId(),
//                selectedPlan.getTargetNamespace()
//        );

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Plan> planValidate(@Valid Plan plan) {
        return null;
    }
}
