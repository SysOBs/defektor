package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.PlanData;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.model.Slave;
import pt.uc.sob.defektor.server.model.SystemConfig;

public abstract class Mapper {

    public static <T, E> T convertToDTO(E e)  {
        if (e instanceof PlanData)
            return (T) PlanMapper.convertToDTO((PlanData) e);
        else if (e instanceof SlaveData)
            return (T) SlaveMapper.convertToDTO((SlaveData) e);
        else if (e instanceof SystemConfigData)
            return (T) SystemConfigMapper.convertToDTO((SystemConfigData) e);
        return null;
    }

    public static <T, E> T convertToDAO(E e) {
        if (e instanceof Plan)
            return (T) PlanMapper.convertToDAO((Plan) e);
        else if (e instanceof Slave)
            return (T) SlaveMapper.convertToDAO((Slave) e);
        else if (e instanceof SystemConfig)
            return (T) SystemConfigMapper.convertToDAO((SystemConfig) e);
        return null;
    }

}
