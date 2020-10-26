package pt.uc.sob.defektor.server;

import io.fabric8.kubernetes.api.model.EnvVar;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.injectors.Injektors;
import pt.uc.sob.defektor.server.model.Plan;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class Orchestrator {

    @Async
    public void runProcess(Plan plan) {

        int injectionsSize = plan.getInjektions().size();
        UUID planUUID = plan.getId();
        String targetNamespace = plan.getTargetNamespace();

        int replicas;
        int planDuration;
        List<EnvVar> environmentVariables;

        for(int i = 0; i < injectionsSize; i++) {
            planDuration = plan.getInjektions().get(i).getWorkLoad().getDuration();
            environmentVariables = Utils.stringEnvToObject(plan.getInjektions().get(i).getWorkLoad().getEnv());
            replicas = plan.getInjektions().get(i).getWorkLoad().getReplicas();

            WorkloadGenerator.deployWorkloadGenerator(planUUID, targetNamespace, environmentVariables, replicas);
            sleep(planDuration);
            WorkloadGenerator.stopWorkLoadGenerator(planUUID, targetNamespace);
            Injektors.deployInjection(planUUID, targetNamespace, "/home/claro/Desktop/robot-shop-pod-cpu-hog.yaml");
            //GET TO KNOW WHEN POD IS RUNNING INSTEAD OF 1 MIN DELAY
            sleep(60);


            /*
                TO-DO:
                    - DELETE ELASTIC SEARCH DATA
             */

            WorkloadGenerator.deployWorkloadGenerator(planUUID, targetNamespace, environmentVariables, replicas);
            sleep(planDuration);
            WorkloadGenerator.stopWorkLoadGenerator(planUUID, targetNamespace);
            Injektors.removeInjection(planUUID, targetNamespace, "/home/claro/Desktop/robot-shop-pod-cpu-hog.yaml");
        }
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
