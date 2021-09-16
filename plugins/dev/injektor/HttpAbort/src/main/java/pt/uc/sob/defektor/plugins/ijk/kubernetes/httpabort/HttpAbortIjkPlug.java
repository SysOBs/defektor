package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
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

import static pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort.Utils.*;

public class HttpAbortIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private static final String PREFIX = "http-abort";
    private static final String SUFFIX = ".yaml";
    private static final String MANIFEST_NAME = "virtual-service-http-abort.yaml";
    private File yamlFile = null;
    private Param param;

    public HttpAbortIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {
        
    }

    @Override
    public void performInjection(IjkParams ijkParam) {
        param = Utils.jsonToObject(ijkParam.getJsonIjkParams().toString());
        InputStream in = HttpAbortIjkPlug.class.getClassLoader().getResourceAsStream(MANIFEST_NAME);
        try {
            this.yamlFile =
                    stringBuilderToTempFile(
                            changedYAMLManifest(in, param),
                            PREFIX,
                            SUFFIX
                    );

            this.system.createOrReplaceCustomResource(
                    buildCustomResourceDefinitionContext(),
                    new FileInputStream(yamlFile),
                    param.getNamespace()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopInjection() {
        this.system.deleteCustomResource(buildCustomResourceDefinitionContext(), param.getNamespace(), param.getService() + "-http-abort");
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
