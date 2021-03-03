package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.WorkLoadData;
import pt.uc.sob.defektor.server.model.WorkLoad;

public class WorkLoadMapper {

    public static WorkLoad convertToDAO(WorkLoadData workLoadData) {
        WorkLoad workLoad = new WorkLoad();
        workLoad.setImage(DockerImageMapper.convertToDAO(workLoadData.getImage()));
        workLoad.setCmd(workLoadData.getCmd());
        workLoad.setDuration(workLoadData.getDuration());
        workLoad.setEnv(workLoadData.getEnv());

        return workLoad;
    }

    public static WorkLoadData convertToDTO(WorkLoad workLoad) {

        WorkLoadData workLoadData = new WorkLoadData();
        workLoadData.setImage(DockerImageMapper.convertToDTO(workLoad.getImage()));
        workLoadData.setCmd(workLoad.getCmd());
        workLoadData.setDuration(workLoad.getDuration());
        workLoadData.setEnv(workLoad.getEnv());

        return workLoadData;
    }
}
