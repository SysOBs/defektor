package pt.uc.sob.defektor.common.com.sysconfigs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class SystemConfig {
    JSONObject jsonSysConfig;
}

