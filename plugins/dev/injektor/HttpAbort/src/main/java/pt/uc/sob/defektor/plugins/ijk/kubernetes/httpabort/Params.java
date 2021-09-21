package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Params implements Serializable {
    private String namespace;
    private String service;
    private String host;
    private String httpStatus;
    private String faultOccurrence;
}