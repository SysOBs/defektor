package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.InjektionData;
import pt.uc.sob.defektor.server.model.Injektion;
import pt.uc.sob.defektor.server.model.WorkLoad;

public abstract class InjektionMapper {
    public static Injektion convertToDAO(InjektionData injektionData) {
        Injektion injektion = new Injektion();
        injektion.setIjk(injektionData.getIjk());
        injektion.setTarget(TargetMapper.convertToDAO(injektionData.getTarget()));
        injektion.setWorkLoad(WorkLoadMapper.convertToDAO(injektionData.getWorkLoad()));

        return injektion;

    }

    public static InjektionData convertToDTO(Injektion injektion) {
        InjektionData injektionData = new InjektionData();
        injektionData.setIjk(injektion.getIjk());
        injektionData.setTarget(TargetMapper.convertToDTO(injektion.getTarget()));
        injektionData.setWorkLoad(WorkLoadMapper.convertToDTO(injektion.getWorkLoad()));

        return injektionData;
    }
}
