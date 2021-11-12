package pt.uc.sob.defektor.server.orchestrator.campaign;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum CampaignStatus {
    WAITING_TO_START,
    RUNNING,
    FINISHED
}