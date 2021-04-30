package pt.uc.sob.defektor.common.com.sysconfigs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VMConfig implements SysConfigInterface {
    private String username;
    private String host;
    private Integer port;
    private String privateKey;
}
