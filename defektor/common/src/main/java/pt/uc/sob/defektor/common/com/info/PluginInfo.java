package pt.uc.sob.defektor.common.com.info;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PluginInfo implements Serializable {
    private String name;
    private String type;
}
