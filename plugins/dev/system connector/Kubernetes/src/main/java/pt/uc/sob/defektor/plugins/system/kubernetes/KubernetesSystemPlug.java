package pt.uc.sob.defektor.plugins.system.kubernetes;

import pt.uc.sob.defektor.common.config.SystemConfig;
import pt.uc.sob.defektor.common.data.target_types.KubernetesTargetType;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class KubernetesSystemPlug extends SystemConnectorPlug {

    private File yamlFile;

    public KubernetesSystemPlug(SystemConfig config) {
        super(config);
    }

    @Override
    public void help() {
        // TODO: implement help

    }

    @Override
    public void configure() {
        // Nothing to configure for Kubernetes.
        // We assume that the user has already configured the cluster in their local machine.
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return List.of(KubernetesTargetType.values());
    }

    public void createOrReplaceResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        yamlFile = Utils.inputStreamToTempFile(yamlFileStream);
        String applyCommand = "kubectl apply -f " + yamlFile.getAbsolutePath() + " --namespace=" + namespace;
        execCommand(applyCommand);
    }

    public void deleteResource(InputStream yamlFileStream, String namespace) throws CampaignException {
        String deleteCommand = "kubectl delete -f " + yamlFile.getAbsolutePath() + " --namespace=" + namespace;
        execCommand(deleteCommand);
    }

    private void execCommand(String command) throws CampaignException {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            throw new CampaignException(ex.getMessage());
        }
    }
}

