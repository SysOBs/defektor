package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpAbortIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private static final String PREFIX = "http-abort";
    private static final String SUFFIX = ".yaml";
    private static final String MANIFEST_NAME = "virtual-service-http-abort.yaml";
    private File yamlFile = null;

    public HttpAbortIjkPlug(SystemPlug system) {
        super(system);
    }

    @Override
    public void performInjection(IjkParam ijkParam) {
        Param param = new Param("uc1", "web","web.uc-1.svc.cluster.local","500", "100");
        InputStream in = HttpAbortIjkPlug.class.getClassLoader().getResourceAsStream(MANIFEST_NAME);

        try {
            yamlFile = Utils.streamToTempFile(in, PREFIX, SUFFIX);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.changeYAMLManifest(yamlFile, param);
        this.system.applyDeployment(yamlFile.getAbsolutePath());
    }


    @Override
    public void stopInjection() {
        System.out.println(yamlFile.getAbsolutePath());
        this.system.deleteDeployment(yamlFile.getAbsolutePath());
    }


    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                //TODO POD NETWORK??
                add(TargetType.POD);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

    @Override
    public Class getTheNameOfTheClass() {
        return null;
    }

}
