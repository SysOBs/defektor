package pt.uc.sob.defektor.server.api.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.uc.sob.defektor.server.orchestrator.campaign.CampaignStatus;
import pt.uc.sob.defektor.server.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CampaignData implements Serializable {
    private UUID id;
    private String name;
    private CampaignStatus status;
    private String startTimestamp;
    private String endTimestamp;
    private Integer currentInjection;
    private Integer totalInjections;
    private List<InjectionData> injections;

    public CampaignData(UUID id, String name, Integer totalInjections) {
        this.id = id;
        this.name = name;
        this.status = CampaignStatus.WAITING_TO_START;
        this.startTimestamp = Utils.Time.getCurrentTimestamp();
        this.endTimestamp = "";
        this.currentInjection = 0;
        this.totalInjections = totalInjections;
        this.injections = new ArrayList<>();
    }

    public void incrementCurrentInjection() {
        this.currentInjection += 1;
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
