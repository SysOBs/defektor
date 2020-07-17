package pt.uc.defektor.injektors.istio.virtualservice;

public class Destination {
    private String host = null;
    private String subset = null;

    public Destination(String host) {
        this.host = host;
    }

    public Destination(String host, String subset) {
        this.host = host;
        this.subset = subset;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSubset() {
        return subset;
    }

    public void setSubset(String subset) {
        this.subset = subset;
    }
}
