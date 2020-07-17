package pt.uc.defektor.injektors.istio.virtualservice;

public class FaultYAML {

    private String apiVersion = "networking.istio.io/v1alpha3";
    private String kind = "VirtualService";
    private Metadata metadata = new Metadata();
    private Spec spec = new Spec();

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
