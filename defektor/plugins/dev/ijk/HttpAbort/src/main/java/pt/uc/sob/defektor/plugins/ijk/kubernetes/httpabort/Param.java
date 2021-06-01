package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Param {
    private String namespace;
    private String service;
    private String host;
    private String httpStatus;
    private String faultOccurrence;
}