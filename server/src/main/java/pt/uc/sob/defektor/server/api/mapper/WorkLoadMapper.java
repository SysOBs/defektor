package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.WorkLoadData;
import pt.uc.sob.defektor.server.model.WorkLoad;

import java.util.stream.Collectors;

public class WorkLoadMapper {

    public static WorkLoadData convertToDAO(WorkLoad workLoad) {

        WorkLoadData workLoadData = new WorkLoadData();
        workLoadData.setImage(DockerImageMapper.convertToDAO(workLoad.getImage()));
        workLoadData.setCmd(workLoad.getCmd());
        workLoadData.setDuration(workLoad.getDuration());
        workLoadData.setReplicas(workLoad.getReplicas());
        workLoadData.setSlaves(workLoad.getSlaves());
        workLoadData.setEnv(
                workLoad.getEnv()
                        .stream()
                        .map(KeyValueMapper::convertToDAO)
                        .collect(Collectors.toList())
        );

        return workLoadData;
    }

    public static WorkLoad convertToDTO(WorkLoadData workLoadData) {
        WorkLoad workLoad = new WorkLoad();
        workLoad.setImage(DockerImageMapper.convertToDTO(workLoadData.getImage()));
        workLoad.setCmd(workLoadData.getCmd());
        workLoad.setDuration(workLoadData.getDuration());
        workLoad.setReplicas(workLoadData.getReplicas());
        workLoad.setSlaves(workLoadData.getSlaves());
        workLoad.setEnv(
                workLoadData.getEnv()
                .stream()
                .map(KeyValueMapper::convertToDTO)
                .collect(Collectors.toList())
        );

        return workLoad;
    }
}
