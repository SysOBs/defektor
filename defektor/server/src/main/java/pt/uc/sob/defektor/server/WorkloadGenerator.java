package pt.uc.sob.defektor.server;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import pt.uc.sob.defektor.server.kubernetes.KubernetesIntegrator;
import pt.uc.sob.defektor.server.model.WorkLoad;
import pt.uc.sob.defektor.server.api.utils.Utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

public class WorkloadGenerator extends WorkLoad {

    public WorkloadGenerator() {
        super();
    }

    private Deployment createWorkloadDeployment(UUID planUUID, List<EnvVar> environmentVariables){

        Map<String, Quantity> resourceLimits = new HashMap<>();
        resourceLimits.put("cpu", new Quantity("200m"));
        resourceLimits.put("memory", new Quantity("200Mi"));

        Map<String, Quantity> resourceRequests = new HashMap<>();
        resourceRequests.put("cpu", new Quantity("100m"));
        resourceRequests.put("memory", new Quantity("100Mi"));

        String imageName = this.getImage().getUser() + "/" + this.getImage().getName() + ":" + this.getImage().getTag();


        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                    .withName("load-" + planUUID)
                    .addToLabels("service", "load")
                .endMetadata()
                .withNewSpec()
                    .withReplicas(this.getReplicas())
                    .withNewSelector()
                        .addToMatchLabels("service", "load")
                    .endSelector()
                    .withNewTemplate()
                        .withNewMetadata()
                            .addToLabels("service", "load")
                        .endMetadata()
                        .withNewSpec()
                            .addNewContainer()
                                .withName("load")
                                .withEnv(environmentVariables)
                                .withImage(imageName)
                                .withNewResources()
                                    .withLimits(resourceLimits)
                                    .withRequests(resourceRequests)
                                .endResources()
                            .endContainer()
                        .endSpec()
                    .endTemplate()
                .endSpec()
                .build();

        return deployment;
    }

    public void deployWorkloadGenerator(@NotNull @Valid UUID planUUID, String targetNamespace) {
        Deployment deployment = createWorkloadDeployment(planUUID, Utils.stringEnvToObject(this.getEnv()));
        KubernetesIntegrator.applyDeployment(deployment, targetNamespace);

        //IMPROVE LOGGING
        System.out.println("Plan " + planUUID + " Workload Gen started at: " + Utils.getStringedCurrentDate());
    }

    public static void stopWorkLoadGenerator(UUID planUUID, String targetNamespace) {
        KubernetesIntegrator.deleteDeployment(planUUID, targetNamespace);
        System.out.println("Plan " + planUUID + " Workload Gen stopped at: " + Utils.getStringedCurrentDate());
    }

//    private static List<Env> setEnvironmentVariables(List<List<String>> environmentVariables){
//        List<Env> envList = new ArrayList<>();
//        for(int i = 0; i < environmentVariables.size(); i++){
//            envList.add(new Env(environmentVariables.get(i).get(0), environmentVariables.get(i).get(1)));
//        }
//        return envList;
//    }
}
