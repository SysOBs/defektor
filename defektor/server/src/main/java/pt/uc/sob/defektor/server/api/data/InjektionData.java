package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjektionData implements Serializable {
  private String ijk;
  private WorkLoadData workLoad;
  private TargetData target;
}

