package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.campaign.run.data.RunStatus;
import pt.uc.sob.defektor.server.model.Run;

public class RunMapper {

    public static RunData convertToDAO(Run run) {
        RunData runData = new RunData();
        runData.setMessage(run.getMessage());
        runData.setStatus(RunStatus.valueOf(run.getStatus()));
        runData.setRunNumber(run.getRunNumber());
        runData.setStartTimestamp(run.getStartTimestamp());
        runData.setEndTimestamp(run.getEndTimestamp());
        return runData;
    }

    public static Run convertToDTO(RunData runData) {
        Run run = new Run();
        run.setMessage(runData.getMessage());
        run.setStatus(runData.getStatus().toString());
        run.setRunNumber(runData.getRunNumber());
        run.setStartTimestamp(runData.getStartTimestamp());
        run.setEndTimestamp(runData.getEndTimestamp());
        return run;
    }
    
    
}
