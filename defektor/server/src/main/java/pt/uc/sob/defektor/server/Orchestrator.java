package pt.uc.sob.defektor.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.injectors.ArbitraryYamlInjektor;
import pt.uc.sob.defektor.server.injectors.PodCpuHog;
import pt.uc.sob.defektor.server.injectors.PodDelete;
import pt.uc.sob.defektor.server.model.Ijk;
import pt.uc.sob.defektor.server.model.Injektion;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.model.WorkLoad;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Service
public class Orchestrator {

    @Async
    public void runProcess(Plan plan) {

        for (Injektion injektion: plan.getInjektions()) {
            WorkLoad workLoad = injektion.getWorkLoad();
            WorkloadGenerator workloadGenerator = workloadComposer(workLoad);

            workloadGenerator.deployWorkloadGenerator(plan.getId(), plan.getTargetNamespace());
            sleep(workLoad.getDuration());

            workloadGenerator.stopWorkLoadGenerator(plan.getId(), plan.getTargetNamespace());

            Ijk ijk;
            switch (injektion.getIjk()) {
                case "pod_cpu_hog":
                    ijk = new PodCpuHog();
                    ijk.setName(injektion.getIjk());

                    break;
                case "pod_delete":
                    ijk = new PodDelete();
                    break;
            }
            try {
                ArbitraryYamlInjektor.deployInjection(
                        plan.getId(),
                        plan.getTargetNamespace(),
                        Paths.get(Orchestrator.class.getResource("/ChaosEngineDeployments/pod_cpu_hog.yaml").toURI()).toFile().getAbsolutePath()
                );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            /*
                TO-DO:
                    - DELETE ELASTIC SEARCH DATA.
             */

            sleep(60);

            workloadGenerator.deployWorkloadGenerator(plan.getId(), plan.getTargetNamespace());
            sleep(workLoad.getDuration());
            workloadGenerator.stopWorkLoadGenerator(plan.getId(), plan.getTargetNamespace());

            try {
                ArbitraryYamlInjektor.removeInjection(plan.getId(),
                        plan.getTargetNamespace(),
                        Paths.get(Orchestrator.class.getResource("/ChaosEngineDeployments/pod_cpu_hog.yaml").toURI()).toFile().getAbsolutePath()
                );
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private WorkloadGenerator workloadComposer(@Valid WorkLoad workLoad) {

        WorkloadGenerator workLoadGenerator = new WorkloadGenerator();
        workLoadGenerator.setCmd(workLoad.getCmd());
        workLoadGenerator.setDuration(workLoad.getDuration());
        workLoadGenerator.setEnv(workLoad.getEnv());
        workLoadGenerator.setImage(workLoad.getImage());
        workLoadGenerator.setReplicas(workLoad.getReplicas());
        workLoadGenerator.setSlaves(workLoad.getSlaves());

        return workLoadGenerator;
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
