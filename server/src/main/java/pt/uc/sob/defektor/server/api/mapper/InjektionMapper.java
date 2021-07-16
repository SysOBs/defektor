package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.model.Injektion;

public class InjektionMapper {
    public static Injektion convertToDTO(InjektionData injektionData) {
        Injektion injektion = new Injektion();
        injektion.setTotalRuns(injektionData.getTotalRuns());
        injektion.setIjk(IjkMapper.convertToDTO(injektionData.getIjk()));
        injektion.setTarget(TargetMapper.convertToDTO(injektionData.getTarget()));
        injektion.setWorkLoad(WorkLoadMapper.convertToDTO(injektionData.getWorkLoad()));

        return injektion;

    }

    public static InjektionData convertToDAO(Injektion injektion) {
        InjektionData injektionData = new InjektionData();
        injektionData.setTotalRuns(injektion.getTotalRuns());
        injektionData.setIjk(IjkMapper.convertToDAO(injektion.getIjk()));
        injektionData.setTarget(TargetMapper.convertToDAO(injektion.getTarget()));
        injektionData.setWorkLoad(WorkLoadMapper.convertToDAO(injektion.getWorkLoad()));

        return injektionData;
    }
}
