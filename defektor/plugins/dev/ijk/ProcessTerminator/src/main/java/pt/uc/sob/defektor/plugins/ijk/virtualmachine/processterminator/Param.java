package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Param {
    String pid;
    String processName;
}
