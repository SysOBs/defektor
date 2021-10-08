package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;
import pt.uc.sob.defektor.server.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignData implements Serializable {
    private UUID id;
    private Integer currentRun;
    private Integer totalRuns;
    private CampaignStatus status;
    private String startTimestamp;
    private String endTimestamp;
    private List<RunData> runs;

    public CampaignData(UUID id, Integer totalRuns) {
        this.id = id;
        this.totalRuns = totalRuns;
        this.currentRun = 0;
        this.status = CampaignStatus.WAITING_TO_START;
        this.startTimestamp = Utils.Time.getCurrentTimestamp();
        this.endTimestamp = "";
        this.runs = new ArrayList<>();
    }

    public void incrementCurrentRun() {
        this.currentRun += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignData that = (CampaignData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
