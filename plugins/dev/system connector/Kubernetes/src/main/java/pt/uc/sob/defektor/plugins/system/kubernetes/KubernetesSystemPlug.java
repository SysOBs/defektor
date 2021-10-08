package pt.uc.sob.defektor.plugins.system.kubernetes;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KubernetesSystemPlug extends SystemConnectorPlug {
    
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


    public void createOrReplaceCustomResource(CustomResourceDefinitionContext customResourceDefinitionContext, InputStream yamlFileStream, String namespace) throws CampaignException {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Map<String, Object> cr = client
                    .customResource(customResourceDefinitionContext)
                    .load(yamlFileStream);
            client.customResource(customResourceDefinitionContext).createOrReplace(namespace, cr);
        } catch (IOException | KubernetesClientException e) {
            throw new CampaignException(e.getMessage());
        }
    }

    public void deleteCustomResource(CustomResourceDefinitionContext customResourceDefinitionContext, String namespace, String name) throws CampaignException {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.customResource(customResourceDefinitionContext).delete(namespace, name);
        } catch (IOException | KubernetesClientException e) {
            throw new CampaignException(e.getMessage());
        }
    }

    public JSONObject listCustomResource(CustomResourceDefinitionContext customResourceDefinitionContext, String namespace) throws CampaignException {
        JSONObject jsonObject;
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            jsonObject = new JSONObject(client
                    .customResource(customResourceDefinitionContext)
                    .list(namespace)
            );
        } catch (KubernetesClientException e) {
            throw new CampaignException(e.getMessage());
        }
        return jsonObject;
    }
}

