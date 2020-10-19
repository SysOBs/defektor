package pt.uc.sob.defektor.server.workloadgen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pt.uc.sob.defektor.server.kubernetes.KubernetesInterface;
import pt.uc.sob.defektor.server.pojos.loadgen.Container;
import pt.uc.sob.defektor.server.pojos.loadgen.Env;
import pt.uc.sob.defektor.server.pojos.loadgen.Manifest;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WorkloadGenerator {

    public static void applyWorkload(UUID uuid, String targetNamespace, List<List<String>> environmentVariables){

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            Manifest loadGenManifest = objectMapper.readValue(new File("/home/goncalo/Desktop/load-gen.yaml"), Manifest.class);
            Container envSelector = loadGenManifest.getSpec().getTemplate().getSpec().getContainers().get(0);
            envSelector.setEnv(setEnvironmentVariables(environmentVariables));
            KubernetesInterface.applyDeployment(objectMapper.writeValueAsString(loadGenManifest), targetNamespace, "/home/goncalo/Desktop/load-gen-" + uuid + ".yaml");


//            System.out.println(loadGenManifest.spec.template.spec.containers.get(0).env.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Env> setEnvironmentVariables(List<List<String>> environmentVariables){
        List<Env> envList = new ArrayList<>();

        for(int i = 0; i < environmentVariables.size(); i++){
            envList.add(new Env(environmentVariables.get(i).get(0), environmentVariables.get(i).get(1)));
        }
        return envList;
    }


}
