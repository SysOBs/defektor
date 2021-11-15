package pt.uc.sob.defektor.server.orchestrator.campaign.injection.run;

public enum RunStatus {
    WAITING_TO_START,
    RUNNING_GOLDEN_RUN,
    RUNNING_FAULT_INJECTION_RUN,
    ABNORMALLY_INTERRUPTED,
    FINISHED
}