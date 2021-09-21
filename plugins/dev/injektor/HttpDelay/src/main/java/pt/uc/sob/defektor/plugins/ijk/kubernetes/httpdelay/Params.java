package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpdelay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Params {
    private String namespace;
    private String service;
    private String host;
    private String faultOccurrence;
    private String fixedDelay;
}
