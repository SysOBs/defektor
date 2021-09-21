package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Params {
    private String pid;
    private String processName;
    private Integer interval;
}
