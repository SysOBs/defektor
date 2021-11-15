package pt.uc.sob.defektor.plugins.system.kubernetes;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class KubernetesSystemPlug extends SystemConnectorPlug {

    private File yamlFile;

    public KubernetesSystemPlug(SystemConfigs configs) {
        super(configs);
        configure();
    }

    @Override
    public void help() {

    }

    @Override
    public void configure() {

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

    public void createOrReplaceResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        try {
            yamlFile = Utils.inputStreamToTempFile(yamlFileStream);
            Runtime.getRuntime().exec("kubectl apply -f " + yamlFile.getAbsolutePath() + "--namespace=" + namespace);
        } catch (IOException ex) {
            throw new CampaignException(ex.getMessage());
        }


    }

    public void deleteResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        try {
//            yamlFile = Utils.inputStreamToTempFile(yamlFileStream);
            Runtime.getRuntime().exec("kubectl delete -f " + yamlFile.getAbsolutePath() + "--namespace=" + namespace);
        } catch (IOException ex) {
            throw new CampaignException(ex.getMessage());
        }
    }
}

