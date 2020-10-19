package pt.uc.sob.defektor.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.pojos.loadgen.Env;
import pt.uc.sob.defektor.server.utils.Utils;
import pt.uc.sob.defektor.server.workloadgen.WorkloadGenerator;

import javax.validation.Valid;
import java.io.*;
import java.rmi.server.ExportException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class PlanController implements PlanApi {

    @Override
    public ResponseEntity<List<Plan>> planList() {
//        InputStream jsonPlan = PlanController.class.getResourceAsStream("/plan.json");
        try {
            //FAZ OVERRIDE DE TUDO QUE TEM NO FICHEIRO
            Plan plan = new ObjectMapper().readValue(new File("/home/goncalo/Desktop/plan.json"), Plan.class);
            List<Plan> planList = Collections.singletonList(plan);
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
                - IMPLEMENT "PLAN ALREADY EXISTS"
         */
        try {
            //PLAN LOCAL FILE PERSISTENCE
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("/home/goncalo/Desktop/plan.json"), plan);

            WorkloadGenerator.applyWorkload(
                    plan.getId(), //PLAN ID
                    plan.getInjektions().get(0).getWorkLoad().getNamespace(), //PLAN NAMESPACE
                    Utils.stringListSplitter(plan.getInjektions().get(0).getWorkLoad().getEnv(), "=") //ENV VARIABLES
            );
            return new ResponseEntity<>(plan, HttpStatus.CREATED);
        } catch (Exception e) {
            //BAD REQUEST
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Plan> planGet(UUID planId) {
        try {
            //FAZ OVERRIDE DE TUDO QUE TEM NO FICHEIRO
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
        return null;
    }

    @Override
    public ResponseEntity<Plan> planValidate(@Valid Plan plan) {


        return null;
    }
}
