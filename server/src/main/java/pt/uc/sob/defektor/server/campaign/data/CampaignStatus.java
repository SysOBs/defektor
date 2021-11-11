package pt.uc.sob.defektor.server.campaign.data;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum CampaignStatus {
    WAITING_TO_START,
    RUNNING,
    FINISHED
}