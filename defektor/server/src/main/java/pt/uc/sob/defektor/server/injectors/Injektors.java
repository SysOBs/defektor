package pt.uc.sob.defektor.server.injectors;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import pt.uc.sob.defektor.server.Utils;
import pt.uc.sob.defektor.server.kubernetes.KubernetesIntegrator;

import java.io.IOException;
import java.util.UUID;

public class Injektors {

    /*
        WORKFLOW:
            - CHECK IF LITMUS AND ISTIO ARE INSTALLED
            - CHECK IF LITMUS CHAOS EXPERIMENTS ARE INSTALLED
            - INSTALL RBAC
            - DEPLOY INJECTION MANIFEST
     */

    private void createInjektionDeployment() {


    }

    public static void deployInjection(UUID planUUID, String targetNamespace, String fileName) {
        KubernetesIntegrator.applyDeploymentByCommand(fileName, targetNamespace);
        System.out.println("Plan " + planUUID + " injected at: " + Utils.getStringedCurrentDate());
    }

    public static void removeInjection(UUID planUUID, String targetNamespace, String fileName) {
        KubernetesIntegrator.deleteDeploymentByCommand(fileName, targetNamespace);
        System.out.println("Plan " + planUUID + " removed failure at: " + Utils.getStringedCurrentDate());

    }
}
