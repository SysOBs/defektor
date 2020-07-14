package pt.uc.sob.defektor.app.fault.istio.virtualservice;

public class Metadata {

    private String name = null;
    private String namespace = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
