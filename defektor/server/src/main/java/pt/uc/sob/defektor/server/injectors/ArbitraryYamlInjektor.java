package pt.uc.sob.defektor.server.injectors;

import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.kubernetes.KubernetesController;
import pt.uc.sob.defektor.server.model.Ijk;

import java.util.UUID;

public class ArbitraryYamlInjektor extends Ijk {

    /*
        WORKFLOW:
            - CHECK IF LITMUS AND ISTIO ARE INSTALLED
            - CHECK IF LITMUS CHAOS EXPERIMENTS ARE INSTALLED
            - INSTALL RBAC
            - DEPLOY INJECTION MANIFEST
     */

    public static void deployInjection(UUID planUUID, String targetNamespace, String fileName) {
        KubernetesController.applyDeploymentByCommand(fileName, targetNamespace);
        System.out.println("Plan " + planUUID + " injected at: " + Utils.getStringedCurrentDate());
    }

    public static void removeInjection(UUID planUUID, String targetNamespace, String fileName) {
        KubernetesController.deleteDeploymentByCommand(fileName, targetNamespace);
        System.out.println("Plan " + planUUID + " removed failure at: " + Utils.getStringedCurrentDate());
    }
}
