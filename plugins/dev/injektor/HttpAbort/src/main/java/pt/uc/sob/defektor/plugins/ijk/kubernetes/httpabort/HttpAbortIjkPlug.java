package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpAbortIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private Params params;
    private InputStream yamlInputStream;

    public HttpAbortIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(IjkParams ijkParam) throws CampaignException {
        this.params = Utils.JSON.jsonToObject(ijkParam.getJsonIjkParams().toString());

        InputStream in = HttpAbortIjkPlug.class
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
                add(TargetType.SERVICE);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

}
