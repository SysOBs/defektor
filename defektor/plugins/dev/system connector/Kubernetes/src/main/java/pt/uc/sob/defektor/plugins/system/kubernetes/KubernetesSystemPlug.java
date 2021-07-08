package pt.uc.sob.defektor.plugins.system.kubernetes;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KubernetesSystemPlug extends SystemConnectorPlug {
    
    public KubernetesSystemPlug(SystemConfig configuration) {
        super(configuration);
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


    public void createOrReplaceCustomResource(CustomResourceDefinitionContext customResourceDefinitionContext, InputStream yamlFileStream, String namespace) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            Map<String, Object> cr = client
                    .customResource(customResourceDefinitionContext)
                    .load(yamlFileStream);
            client.customResource(customResourceDefinitionContext).create(namespace, cr);
        } catch (IOException e) {
            //TODO SOMETHING
            e.printStackTrace();
        }
    }

    public void deleteCustomResource(CustomResourceDefinitionContext customResourceDefinitionContext, String namespace, String name) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            client.customResource(customResourceDefinitionContext).delete(namespace, name);
        } catch (IOException e) {
            //TODO SOMETHING
            e.printStackTrace();
        }
    }
}

