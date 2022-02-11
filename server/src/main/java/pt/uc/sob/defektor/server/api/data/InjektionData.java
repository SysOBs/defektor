package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjektionData implements Serializable {
  private Integer totalRuns;
  private Map<String, IjkData> ijk;
  private WorkLoadData workLoad;
  private DataCollectorData dataCollector;
  private TargetData target;
}

