package pt.uc.sob.defektor.server.api.data;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.campaign.data.CampaignStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CampaignData {
    private UUID id;
    private Integer currentRun = 1;
    private Integer totalRuns;
    private CampaignStatus status = CampaignStatus.STOPPED;

    public CampaignData(UUID id, Integer totalRuns) {
        this.id = id;
        this.totalRuns = totalRuns;
    }

    public void incrementCurrentRun() {
        this.currentRun += 1;
    }
}
