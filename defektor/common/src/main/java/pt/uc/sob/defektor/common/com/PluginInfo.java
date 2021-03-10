package pt.uc.sob.defektor.common.com;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PluginInfo {
    private String name;
    private Date timestamp;
    private String url;
}
