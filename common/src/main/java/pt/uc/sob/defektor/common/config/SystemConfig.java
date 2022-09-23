package pt.uc.sob.defektor.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class SystemConfig {
    JSONObject jsonConfig;
}
