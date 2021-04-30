package pt.uc.sob.defektor.common.com.params;

import lombok.Data;

@Data
public class ProcessTerminatorParam implements ParamInterface {
    public String processName;
    public Integer pid;

    public ProcessTerminatorParam(String processName) {
        this.processName = processName;
    }

    public ProcessTerminatorParam(Integer pid) {
        this.pid = pid;
    }
}
