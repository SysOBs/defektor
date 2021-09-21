package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpAbortIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private static final String PREFIX = "http-abort";
    private static final String SUFFIX = ".yaml";
    private static final String MANIFEST_NAME = "virtual-service-http-abort.yaml";
    private File yamlFile = null;
    private Params params;

    public HttpAbortIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {
        
    }

    @Override
    public void performInjection(IjkParams ijkParam) throws CampaignException {
        params = Utils.jsonToObject(ijkParam.getJsonIjkParams().toString());
        InputStream in = HttpAbortIjkPlug.class.getClassLoader().getResourceAsStream(MANIFEST_NAME);
        try {
            this.yamlFile =
                    Utils.stringBuilderToTempFile(
                            Utils.changedYAMLManifest(in, params),
                            PREFIX,
                            SUFFIX
                    );

            this.system.createOrReplaceCustomResource(
                    Utils.buildCustomResourceDefinitionContext(),
                    new FileInputStream(yamlFile),
                    params.getNamespace()
            );
        } catch (IOException | CampaignException e) {
            throw new CampaignException(e.getMessage());
        }
    }

    @Override
    public void stopInjection() throws CampaignException {
        this.system.deleteCustomResource(Utils.buildCustomResourceDefinitionContext(), params.getNamespace(), params.getService() + "-http-abort");
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.SERVICE);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

}
