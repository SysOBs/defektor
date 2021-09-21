package pt.uc.sob.defektor.plugins.system.virtualmachine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Configs {
    private String username;
    private String host;
    private Integer port;
    private String privateKey;
}
