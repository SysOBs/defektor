package pt.uc.sob.defektor.server.kubernetes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KubernetesInterface {

    public static void applyDeployment(String deployment, String targetNamespace, String fileName){
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(deployment);
            fileWriter.close();

            String command = "kubectl apply -n " + targetNamespace + " -f " + fileName;
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDeployment(String deployment, String targetNamespace, String fileName){
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(deployment);
            fileWriter.close();

            String command = "kubectl delete -n " + targetNamespace + " -f " + fileName;
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
