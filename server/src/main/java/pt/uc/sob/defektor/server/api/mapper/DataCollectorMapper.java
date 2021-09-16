package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.DataCollectorData;
import pt.uc.sob.defektor.server.api.data.SSHCredentialsData;
import pt.uc.sob.defektor.server.model.DataCollector;
import pt.uc.sob.defektor.server.model.SSHCredentials;

import java.util.stream.Collectors;

public class DataCollectorMapper {

    public static DataCollectorData convertToDAO(DataCollector dataCollector) {
        DataCollectorData dataCollectorData = new DataCollectorData();
        dataCollectorData.setName(dataCollector.getName());
        dataCollectorData.setParams(
                dataCollector.getParams().stream()
                        .map(KeyValueMapper::convertToDAO)
                        .collect(Collectors.toList())
        );
        return dataCollectorData;
    }


    public static DataCollector convertToDTO(DataCollectorData dataCollectorData) {
        DataCollector dataCollector = new DataCollector();
        dataCollector.setName(dataCollector.getName());
        dataCollector.setParams(
                dataCollectorData.getParams().stream()
                        .map(KeyValueMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return dataCollector;
    }
}
