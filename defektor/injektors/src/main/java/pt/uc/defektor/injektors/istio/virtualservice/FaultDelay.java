package pt.uc.defektor.injektors.istio.virtualservice;

import java.util.HashMap;
import java.util.Map;

public class FaultDelay extends Fault {
    private Map<String, Object> delay = null;

    public FaultDelay(int seconds, int percentage) {
        this.delay = new HashMap<>() {{
            put("fixedDelay", String.format("%ds", seconds));
            put("percentage", Map.of("value", percentage));
        }};
    }

    public Map<String, Object> getDelay() {
        return delay;
    }

    public void setDelay(Map<String, Object> delay) {
        this.delay = delay;
    }
}
