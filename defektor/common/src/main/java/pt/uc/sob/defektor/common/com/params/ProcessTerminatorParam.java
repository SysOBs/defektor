package pt.uc.sob.defektor.common.com.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProcessTerminatorParam extends AbstractParam {
    public String processName;
    public Integer pid;

    public ProcessTerminatorParam(String processName) {
        this.processName = processName;
    }

    public ProcessTerminatorParam(Integer pid) {
        this.pid = pid;
    }
}
