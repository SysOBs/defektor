package pt.uc.sob.defektor.plugins.system.kubernetes;

import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class KubernetesSystemPlug extends SystemPlug {

    private KubernetesClient kubernetesClient;
    private Config config;


    public KubernetesSystemPlug(SystemConfig configuration) {
        super(configuration);
        configure();
    }

    @Override
    public void help() {

    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.POD);
                add(TargetType.NODE);
            }
        };
    }

    @Override
    public void configure() {
//        config = Utils.jsonToObject(configuration.getJsonSysConfig().toString());
        kubernetesClient = new DefaultKubernetesClient();
    }

    public void createOrReplaceResourceList(InputStream inputStream, String namespace) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinition crd = client.apiextensions().v1().customResourceDefinitions()
                    .load(new FileInputStream("/tmp/http-abort16180032887145986177.yaml"))
                    .get();
            client.apiextensions().v1().customResourceDefinitions().createOrReplace(crd);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void applyDeployment(String filePath) {
        try {
            Runtime.getRuntime().exec("kubectl apply -f " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeployment(String filePath) {
        try {
            Runtime.getRuntime().exec("kubectl delete -f " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

