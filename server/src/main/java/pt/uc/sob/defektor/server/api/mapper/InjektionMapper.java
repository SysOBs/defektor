package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.model.Injektion;

public class InjektionMapper {

    public static Injektion convertToDTO(InjektionData injektionData) {
        Injektion injektion = new Injektion();
        injektion.setTotalRuns(injektionData.getTotalRuns());
        injektion.setIjk(IjkMapper.convertToDTO(injektionData.getIjk()));
        injektion.setWorkLoad(WorkLoadMapper.convertToDTO(injektionData.getWorkLoad()));
        injektion.setDataCollector(DataCollectorMapper.convertToDTO(injektionData.getDataCollector()));
        return injektion;

    }

    public static InjektionData convertToDAO(Injektion injektion) {
        InjektionData injektionData = new InjektionData();
        injektionData.setTotalRuns(injektion.getTotalRuns());
        injektionData.setIjk(IjkMapper.convertToDAO(injektion.getIjk()));
        injektionData.setWorkLoad(WorkLoadMapper.convertToDAO(injektion.getWorkLoad()));
        injektionData.setDataCollector(DataCollectorMapper.convertToDAO(injektion.getDataCollector()));
        return injektionData;
    }
}
