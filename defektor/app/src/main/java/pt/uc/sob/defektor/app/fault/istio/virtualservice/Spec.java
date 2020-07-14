package pt.uc.sob.defektor.app.fault.istio.virtualservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Spec {
    private List<String> hosts = null;
    private List<Map<String, Object>> http = null;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(String host) {
        if (hosts == null)
            this.hosts = new ArrayList<>();
        this.hosts.add(host);
    }

    public List<Map<String, Object>> getHttp() {
        return http;
    }

    public void setHttp(List<Map<String, Object>> http) {
        this.http = http;
    }
}
