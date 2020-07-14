package pt.uc.sob.defektor.app.fault.istio.virtualservice;

import java.util.HashMap;
import java.util.Map;

public class FaultAbort extends Fault {
    private Map<String, Object> abort = null;

    public FaultAbort(int status, int percentage) {
        this.abort = new HashMap<>() {{
            put("httpStatus", status);
            put("percentage", Map.of("value", percentage));
        }};
    }

    public Map<String, Object> getAbort() {
        return abort;
    }

    public void setAbort(Map<String, Object> abort) {
        this.abort = abort;
    }
}
