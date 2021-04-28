package pt.uc.sob.defektor.common.plugins.system.kubernetes;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.SystemConfiguration;
import pt.uc.sob.defektor.common.com.TargetType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KubernetesSystemPlug extends SystemPlug {

    ApiClient client;

    public KubernetesSystemPlug(SystemConfiguration configuration) {
        super(configuration);
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
    protected void configure(SystemConfiguration systemConfiguration) {
        String kubeConfigPath = "yaml.yaml";

        V1Deployment v1Deployment = new V1Deployment();

        try {
            this.client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
            Configuration.setDefaultApiClient(client);
            CoreV1Api api = new CoreV1Api();

            V1PodList list =
                    api.listNamespacedPod("robot-shop", null, null, null, null, null, null, null, null, null);
            for (V1Pod item : list.getItems()) {
                System.out.println(item.getMetadata().getName());
            }

        } catch (ApiException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
