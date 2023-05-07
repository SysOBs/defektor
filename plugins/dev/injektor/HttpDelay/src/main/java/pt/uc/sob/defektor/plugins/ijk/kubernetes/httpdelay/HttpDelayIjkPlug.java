package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpdelay;

import pt.uc.sob.defektor.common.config.InjektorParams;
import pt.uc.sob.defektor.common.data.Target;
import KubernetesTargetType;
import pt.uc.sob.defektor.common.data.target_types.TargetType;
import pt.uc.sob.defektor.common.exception.CampaignException;
import pt.uc.sob.defektor.common.plugin.abstraction.InjektorPlug;
import pt.uc.sob.defektor.common.plugin.abstraction.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpDelayIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private Params params;
    private InputStream yamlInputStream;

    public HttpDelayIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(InjektorParams ijkParam) throws CampaignException {
        this.params = Utils.JSON.jsonToObject(ijkParam.toString());

        InputStream in = HttpDelayIjkPlug.class
                .getClassLoader()
                .getResourceAsStream(Utils.Strings.MANIFEST_NAME);

        try {
            this.yamlInputStream = new FileInputStream(
                    Utils.stringBuilderToTempFile(
                            Utils.changedYAMLManifest(in, this.params),
                            Utils.Strings.PREFIX,
                            Utils.Strings.SUFFIX
                    )
            );

            this.system.createOrReplaceResource(
                    yamlInputStream,
                    this.params.getNamespace()
            );
        } catch (IOException e) {
            throw new CampaignException(e.getMessage());
        }
    }

    @Override
    public void stopInjection() throws CampaignException {
        this.system.deleteResource(
                yamlInputStream, params.getNamespace()
        );
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(KubernetesTargetType.SERVICE);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }
}
