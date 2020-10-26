package pt.uc.sob.defektor.server;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import pt.uc.sob.defektor.server.kubernetes.KubernetesIntegrator;

import java.util.*;

public class WorkloadGenerator {

    private static Deployment createWorkloadDeployment(UUID planUUID, List<EnvVar> environmentVariables){

//        List
        Map<String, Quantity> resourceLimits = new HashMap<>();
        Map<String, Quantity> resourceRequests = new HashMap<>();

        resourceLimits.put("cpu", new Quantity("200m"));
        resourceLimits.put("memory", new Quantity("200Mi"));

        resourceRequests.put("cpu", new Quantity("100m"));
        resourceRequests.put("memory", new Quantity("100Mi"));

//        resourceLimits.
        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                    .withName("load-" + planUUID)
                    .addToLabels("service", "load")
                .endMetadata()
                .withNewSpec()
                    .withReplicas(2)
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
                                .withImage("robotshop/rs-load:0.4.30")
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

    public static void deployWorkloadGenerator(UUID planUUID, String targetNamespace, List<EnvVar> environmentVariables, int replicas) {
        Deployment deployment = createWorkloadDeployment(planUUID, environmentVariables);
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
