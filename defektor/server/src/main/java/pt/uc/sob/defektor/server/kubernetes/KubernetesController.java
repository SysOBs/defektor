package pt.uc.sob.defektor.server.kubernetes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.kubernetes.client.proto.V1Apps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public abstract class KubernetesController {

    public static void applyDeploymentByCommand(String fileName, String targetNamespace){

        String command = "kubectl apply -n " + targetNamespace + " -f " + fileName;
        try {
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void applyDeployment(Deployment deployment, String namespace){

        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps().deployments().inNamespace(namespace).createOrReplace(deployment);
        }
        catch (KubernetesClientException exception)  {
            exception.printStackTrace();
        }
    }

    public static void deleteDeployment(UUID planUUID, String targetNamespace){
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.apps().deployments().inNamespace(targetNamespace).withName("load-" + planUUID).delete();
        }
        catch (KubernetesClientException exception)  {
            exception.printStackTrace();
        }
    }

    public static void deleteDeploymentByCommand(String fileName, String targetNamespace){
        String command = "kubectl delete -n " + targetNamespace + " -f " + fileName;
        try {
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
