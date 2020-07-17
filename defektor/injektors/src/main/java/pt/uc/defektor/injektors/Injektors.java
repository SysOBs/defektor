package pt.uc.defektor.injektors;

import pt.uc.defektor.injektors.istio.FaultYAMLBuilder;
import pt.uc.defektor.injektors.istio.virtualservice.FaultAbort;
import pt.uc.defektor.injektors.istio.virtualservice.FaultDelay;

public class Injektors {

    public static void main(String[] args) {
        System.out.println("Injektors");

        // Examples
        String faultDelay = new FaultYAMLBuilder()
                .name("fault-delay")
                .namespace("ijk-namespace")
                .host("target-service-1")
                .fault(new FaultDelay(10, 50))
                .genYAML();
        System.out.println(faultDelay);

        String faultAbort = new FaultYAMLBuilder()
                .name("fault-abort")
                .namespace("ijk-namespace")
                .host("target-service-2")
                .fault(new FaultAbort(404, 5))
                .genYAML();
        System.out.println(faultAbort);
    }
}
