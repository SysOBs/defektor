package pt.uc.sob.defektor.plugins.system.virtualmachine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;

@Data
@AllArgsConstructor
@ToString
public class Config {
    private String username;
    private String host;
    private Integer port;
    private String privateKey;
}
