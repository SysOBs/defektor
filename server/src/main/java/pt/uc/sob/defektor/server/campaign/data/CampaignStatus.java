package pt.uc.sob.defektor.server.campaign.data;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum CampaignStatus {
    STOPPED("stopped"),
    RUNNING_GOLDEN_RUN("running a golden run"),
    RUNNING_FAULT_INJECTION_RUN("running a fault injection run"),
    FINISHED("finished");

    private final String status;
}