package pt.uc.sob.defektor.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.injectors.ArbitraryYamlInjektor;
import pt.uc.sob.defektor.server.injectors.PodCpuHog;
import pt.uc.sob.defektor.server.injectors.PodDelete;
import pt.uc.sob.defektor.server.model.Ijk;
import pt.uc.sob.defektor.server.model.Injektion;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.model.WorkLoad;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class Orchestrator {

    private UUID planUUID;
    private String planTargetNamespace;

    @Async
    public void conductProcess(Plan plan) {
        this.planUUID = plan.getId();
        this.planTargetNamespace = plan.getTargetNamespace();

        for (Injektion injektion: plan.getInjektions()) {
            WorkLoad workLoad = injektion.getWorkLoad();
            WorkloadGenerator workloadGenerator = workloadComposer(workLoad);

            /*
                APPLY LOAD GEN TO CLUSTER
             */
            applyLoadGen(workloadGenerator, workLoad.getDuration());

            Ijk ijk = defineInjectionType(injektion.getIjk());
            applyFailureInjection(ijk);

            //GIVE TIME TO INJECTION BE DEPLOYED
            sleep(60);

            applyLoadGen(workloadGenerator, workLoad.getDuration());

            removeFailureInjection(ijk);
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

    private void applyLoadGen(WorkloadGenerator workloadGenerator, int loadDuration ) {
        workloadGenerator.deployWorkloadGenerator(this.planUUID, this.planTargetNamespace);
        sleep(loadDuration);
        workloadGenerator.stopWorkLoadGenerator(this.planUUID, this.planTargetNamespace);
    }

    private Ijk defineInjectionType(String ijkName) {
        Ijk ijk;

        switch (ijkName) {
            case "pod_cpu_hog":
                ijk = new PodCpuHog();
                ijk.setName(ijkName);
                break;
            case "pod_delete":
                ijk = new PodDelete();
                ijk.setName(ijkName);
                break;
            default:
                ijk = null;
        }
        return ijk;
    }

    private void applyFailureInjection(Ijk ijk) {

        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if(manifestAbsolutePath.equals(null)) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

        ArbitraryYamlInjektor.deployInjection(
                this.planUUID,
                this.planTargetNamespace,
                manifestAbsolutePath
        );
    }

    private void removeFailureInjection(Ijk ijk) {
        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if(manifestAbsolutePath.equals(null)) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

        ArbitraryYamlInjektor.removeInjection(
                this.planUUID,
                this.planTargetNamespace,
                manifestAbsolutePath
        );
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}