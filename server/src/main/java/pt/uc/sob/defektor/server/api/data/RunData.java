package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.campaign.run.data.RunStatus;
import pt.uc.sob.defektor.server.utils.Strings;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunData implements Serializable {
    private Integer runNumber;
    private RunStatus status;
    private String message;
    private String startTimestamp;
    private String endTimestamp;
    private DataOutputURIData dataOutputURI;

    public RunData(Integer runNumber) {
        this.runNumber = runNumber;
        this.status = RunStatus.WAITING_TO_START;
        this.message = Strings.Run.WAITING_TO_START;
        this.startTimestamp = "";
        this.endTimestamp = "";
        this.dataOutputURI = new DataOutputURIData();
    }
}
