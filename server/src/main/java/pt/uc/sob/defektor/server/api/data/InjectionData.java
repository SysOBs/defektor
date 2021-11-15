package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.InjectionStatus;
import pt.uc.sob.defektor.server.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionData implements Serializable {
    private Integer injectionNumber;
    private InjectionStatus status;
    private String message;
    private String startTimestamp;
    private String endTimestamp;
    private Integer currentRun;
    private Integer totalRuns;
    private List<RunData> runs;

    public InjectionData(Integer injectionNumber, Integer totalRuns) {
        this.injectionNumber = injectionNumber;
        this.status = InjectionStatus.WAITING_TO_START;
        this.message = "";
        this.startTimestamp = Utils.Time.getCurrentTimestamp();
        this.endTimestamp = "";
        this.currentRun = 0;
        this.totalRuns = totalRuns;
        this.runs = new ArrayList<>();
    }

    public void incrementCurrentRun() {
        this.currentRun += 1;
    }

}
