package pt.uc.sob.defektor.server.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uc.sob.defektor.server.model.Plan;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class MyPlan implements PlanApi {

    @Override
    public ResponseEntity<List<Plan>> planList() {
        Plan plan = new Plan();
        plan.setId(UUID.randomUUID());
        plan.setName("TestPlan");

        List<Plan> planList = Collections.singletonList(plan);

        return new ResponseEntity<>(planList, HttpStatus.OK);
    }
}
