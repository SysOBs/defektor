package pt.uc.sob.defektor.plugins.system.kubernetes;

import pt.uc.sob.defektor.common.com.data.target_types.KubernetesTargetType;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        return List.of(KubernetesTargetType.values());
    }

    public void createOrReplaceResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        try {
            yamlFile = Utils.inputStreamToTempFile(yamlFileStream);
            String applyCommand = "kubectl apply -f " + yamlFile.getAbsolutePath() + " --namespace=" + namespace;
            Runtime.getRuntime().exec(applyCommand);
        } catch (IOException ex) {
            throw new CampaignException(ex.getMessage());
        }
    }

    public void deleteResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        try {
            Runtime.getRuntime().exec("kubectl delete -f " + yamlFile.getAbsolutePath() + " --namespace=" + namespace);
        } catch (IOException ex) {
            throw new CampaignException(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(new KubernetesSystemPlug(null).getTargetTypes());
    }
}

