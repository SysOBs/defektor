package pt.uc.sob.defektor.plugins.datacollector.jaeger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Params {
    private String host;
    private String startTimestamp;
    private String endTimestamp;
}
