package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjektionData implements Serializable {
  private IjkData ijk;
  private WorkLoadData workLoad;
  private TargetData target;
}

