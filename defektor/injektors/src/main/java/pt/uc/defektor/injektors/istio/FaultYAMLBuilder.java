package pt.uc.defektor.injektors.istio;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import pt.uc.defektor.injektors.istio.virtualservice.*;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

// TODO: Write JavaDocs

public class FaultYAMLBuilder {

    private FaultYAML faultYAML = null;
    private Yaml yaml = null;

    public FaultYAMLBuilder() {
        this.faultYAML = new FaultYAML();
        Representer representer = new Representer() {
            @Override
            protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
                // if value of property is null, ignore it.
                if (propertyValue == null) {
                    return null;
                } else {
                    return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
                }
            }
        };
        representer.addClassTag(FaultYAML.class, new Tag("!!defektor"));
        representer.addClassTag(Destination.class, Tag.MAP);
        representer.addClassTag(FaultAbort.class, Tag.MAP);
        representer.addClassTag(FaultDelay.class, Tag.MAP);
        representer.addClassTag(Metadata.class, Tag.MAP);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(representer, dumperOptions);
    }

    public FaultYAMLBuilder name(String name) {
        this.faultYAML.getMetadata().setName(name);
        return this;
    }

    public FaultYAMLBuilder namespace(String namespace) {
        this.faultYAML.getMetadata().setNamespace(namespace);
        return this;
    }

    public FaultYAMLBuilder host(String host) {
        this.faultYAML.getSpec().setHosts(host);
        return this;
    }

    public FaultYAMLBuilder fault(Fault fault) {
        String host = this.faultYAML.getSpec().getHosts().get(0);
        this.faultYAML.getSpec().setHttp(Collections.singletonList(
                new TreeMap<>() {{
                    put("fault", fault);
                    put("route", Collections.singletonList(Map.of("destination", new Destination(host))));
                }}));
        return this;
    }

    public String genYAML() {
        return this.yaml.dump(faultYAML);
    }
}
