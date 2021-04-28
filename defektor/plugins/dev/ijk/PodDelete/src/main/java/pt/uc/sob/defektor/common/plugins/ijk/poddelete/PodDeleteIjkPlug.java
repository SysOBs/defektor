package pt.uc.sob.defektor.common.plugins.ijk.poddelete;

import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.common.com.params.PodDeleteParam;
import pt.uc.sob.defektor.common.plugins.system.kubernetes.KubernetesSystemPlug;

import java.util.ArrayList;
import java.util.List;

public class PodDeleteIjkPlug extends InjektorPlug<KubernetesSystemPlug> {


    public PodDeleteIjkPlug(KubernetesSystemPlug system) {
        super(system);
    }

    @Override
    public void performInjection(AbstractParam abstractParam) {
        PodDeleteParam param = (PodDeleteParam) abstractParam;

        //TODO fetch sample manifest and change some parameters according to the plan
        //TODO apply manifest
    }

    @Override
    public void stopInjection() {
    }

    @Override
    public void setup() {
        //TODO not quite sure if this is needed
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
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
