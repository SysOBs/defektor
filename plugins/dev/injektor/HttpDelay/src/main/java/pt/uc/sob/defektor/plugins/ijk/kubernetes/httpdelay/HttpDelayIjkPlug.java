package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpdelay;

import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class HttpDelayIjkPlug extends InjektorPlug<KubernetesSystemPlug> {


    private File yamlFile = null;
    private Params params;

    public HttpDelayIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(IjkParams ijkParam) throws CampaignException {
        this.params = Utils.JSON.jsonToObject(ijkParam.getJsonIjkParams().toString());

        try {
            applyDetachedVirtualService();

            this.system.createOrReplaceCustomResource(
                    Utils.buildCustomResourceDefinitionContext(),
                    new FileInputStream(yamlFile),
                    this.params.getNamespace()
            );
        } catch (IOException | CampaignException e) {
            throw new CampaignException(e.getMessage());
        }


    }

    private void applyDetachedVirtualService() throws IOException {
        InputStream in = HttpDelayIjkPlug.class
                .getClassLoader()
                .getResourceAsStream(Utils.Strings.MANIFEST_NAME);

        this.yamlFile = Utils.stringBuilderToTempFile(
                Utils.changedYAMLManifest(in, this.params),
                Utils.Strings.PREFIX,
                Utils.Strings.SUFFIX
        );
    }

    @Override
    public void stopInjection() throws CampaignException {
        this.system.deleteCustomResource(
                Utils.buildCustomResourceDefinitionContext(),
                params.getNamespace(),
                params.getService() + "-http-delay")
        ;
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
