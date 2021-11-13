package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.InjectionData;
import pt.uc.sob.defektor.server.model.Injection;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.InjectionStatus;

import java.util.stream.Collectors;

public class InjectionMapper {

    /**
     * Converts Injection DTO object to DAO
     * @param injection Injection DTO object
     * @return          Injection DAO object
     */
    public static InjectionData convertToDAO(Injection injection) {
        InjectionData injectionData = new InjectionData();
        injectionData.setInjectionNumber(injection.getInjectionNumber());
        injectionData.setStatus(InjectionStatus.valueOf(injection.getStatus()));
        injectionData.setMessage(injection.getMessage());
        injectionData.setStartTimestamp(injection.getStartTimestamp());
        injectionData.setEndTimestamp(injection.getEndTimestamp());
        injectionData.setCurrentRun(injection.getCurrentRun());
        injectionData.setTotalRuns(injection.getTotalRuns());
        injectionData.setRuns(
                injection.getRuns().stream()
                        .map(RunMapper::convertToDAO)
                        .collect(Collectors.toList())
        );
        return injectionData;
    }

    /**
     * Converts Injection DAO to DTO object
     * @param injectionData Injection DTO object
     * @return              Injection DAO object
     */
    public static Injection convertToDTO(InjectionData injectionData) {
        Injection injection = new Injection();
        injection.setInjectionNumber(injectionData.getInjectionNumber());
        injection.setStatus(injectionData.getStatus().toString());
        injection.setMessage(injectionData.getMessage());
        injection.setStartTimestamp(injectionData.getStartTimestamp());
        injection.setEndTimestamp(injectionData.getEndTimestamp());
        injection.setCurrentRun(injectionData.getCurrentRun());
        injection.setTotalRuns(injectionData.getTotalRuns());
        injection.setRuns(
                injectionData.getRuns().stream()
                        .map(RunMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return injection;
    }
}
