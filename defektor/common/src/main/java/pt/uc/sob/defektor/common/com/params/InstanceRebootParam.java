package pt.uc.sob.defektor.common.com.params;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstanceRebootParam implements AbstractParam {
    String username;
    String host;
    Integer port;
    String keyDir;
}


