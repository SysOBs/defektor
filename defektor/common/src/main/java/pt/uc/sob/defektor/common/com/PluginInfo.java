package pt.uc.sob.defektor.common.com;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class PluginInfo implements Serializable {
    private String name;
    private Date timestamp;
}
