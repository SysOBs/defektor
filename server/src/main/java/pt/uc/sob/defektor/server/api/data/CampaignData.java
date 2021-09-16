package pt.uc.sob.defektor.server.api.data;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignData implements Serializable {
    private UUID id;
    private Integer currentRun = 1;
    private Integer totalRuns;
    private CampaignStatus status = CampaignStatus.STOPPED;
    private String message;

    public CampaignData(UUID id, Integer totalRuns) {
        this.id = id;
        this.totalRuns = totalRuns;
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
