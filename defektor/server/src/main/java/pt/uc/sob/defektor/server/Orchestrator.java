package pt.uc.sob.defektor.server;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.server.utils.Utils;
import pt.uc.sob.defektor.server.model.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Service
public class Orchestrator {

//    private UUID planUUID;
//    private String planTargetNamespace;

    private IjkParam ijkParam;


    @Async
    public void conductProcess(Plan plan) {

        for (Injektion injektion: plan.getInjektions()) {

            WorkLoad workLoad = injektion.getWorkLoad();
            WorkloadGenerator workloadGenerator = workloadComposer(workLoad);

            //TODO APPLY LOAD GEN (HAVE TO FIGURE A WAY TO MAKE THIS SYS AGNOSTIC)
            applyLoadGen(workloadGenerator, workLoad.getDuration());

            defineSystemType(plan.getSystem().getName());
            defineInjectionType(injektion.getIjk());

            //TODO I must figure what's the best way to parametrize injektors in plan description. Is it possible to be flexible with the json?

            //GIVE TIME TO INJECTION BE DEPLOYED
            //TODO NOT SURE IT IS THE BEST WAY TO DO IT
            sleep(60);

            applyLoadGen(workloadGenerator, workLoad.getDuration());

        }
    }

    private void defineSystemType(String systemType) {
//        systemTaskHandler = new SystemTaskHandler(systemType);
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
        //TODO Yet to do
//        workloadGenerator.deployWorkloadGenerator(this.planUUID, this.planTargetNamespace);
//        sleep(loadDuration);
//        WorkloadGenerator.stopWorkLoadGenerator(this.planUUID, this.planTargetNamespace);
    }

    private void defineInjectionType(String ijkName) {
//        ijkTaskHandler = new IjkTaskHandler(ijkName);
//        switch (ijkName) {
//            case "pod-delete":
//                ijkTaskHandler = new IjkTaskHandler("pod-delete");
//                break;
//            case "http-abort":
//                ijkTaskHandler = new IjkTaskHandler("http-abort");
//                break;
//            case "http-delay":
//                ijkTaskHandler = new IjkTaskHandler("http-delay");
//                break;
//            default:
//                ijkTaskHandler = null;
//        }
    }

    private void applyFailureInjection(Ijk ijk) {

        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if(manifestAbsolutePath == null) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

//        ArbitraryYamlInjektor.deployInjection(
//                this.planUUID,
//                this.planTargetNamespace,
//                manifestAbsolutePath
//        );
    }

    private void removeFailureInjection(Ijk ijk) {
        String manifestAbsolutePath = Utils.getResourceFileAbsolutePath("/ChaosEngineDeployments/" + ijk.getName() + ".yaml");

        if(manifestAbsolutePath == null) {
            System.out.println("ERROR GETTING INJECTION MANIFEST");
            return;
        }

//        ArbitraryYamlInjektor.removeInjection(
//                this.planUUID,
//                this.planTargetNamespace,
//                manifestAbsolutePath
//        );
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
