package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.common.config.InjektorParams;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IjkData implements Serializable {
  private InjektorParams parameters;
}
