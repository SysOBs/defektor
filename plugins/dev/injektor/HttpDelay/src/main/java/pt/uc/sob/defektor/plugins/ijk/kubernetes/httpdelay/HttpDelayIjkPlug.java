package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpdelay;

import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
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

    private static final String PREFIX = "http-delay";
    private static final String SUFFIX = ".yaml";
    private static final String MANIFEST_NAME = "virtual-service-http-delay.yaml";
    private File yamlFile = null;
    private Params params;

    public HttpDelayIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {

    }

    @Override
    public void performInjection(IjkParams ijkParam) {
        params = new Params("uc-1", "web", "web.uc-1.svc.cluster.local", "100", "5");
        InputStream in = HttpDelayIjkPlug.class.getClassLoader().getResourceAsStream(MANIFEST_NAME);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopInjection() {
        this.system.deleteCustomResource(Utils.buildCustomResourceDefinitionContext(), params.getNamespace(), params.getService() + "-http-delay");
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
