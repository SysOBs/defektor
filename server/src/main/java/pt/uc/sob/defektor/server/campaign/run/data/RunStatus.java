package pt.uc.sob.defektor.server.campaign.run.data;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum RunStatus {
    WAITING_TO_START,
    RUNNING_GOLDEN_RUN,
    RUNNING_FAULT_INJECTION_RUN,
    ABNORMALLY_INTERRUPTED,
    FINISHED
}