package pt.uc.sob.defektor.plugins.system.virtualmachine;

import lombok.AllArgsConstructor;
import lombok.Data;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;

@Data
@AllArgsConstructor
public class Config extends AbstractSysConfig {
    private String username;
    private String host;
    private Integer port;
    private String privateKey;
}
