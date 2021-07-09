package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.SystemTypeData;
import pt.uc.sob.defektor.server.model.SystemType;

public class SystemTypeMapper {
    public static SystemTypeData convertToDAO(SystemType systemType) {
        return new SystemTypeData(systemType.getName());
    }

    public static SystemType convertToDTO(SystemTypeData systemTypeData) {
        SystemType systemType = new SystemType();
        systemType.setName(systemTypeData.getName());
        return systemType;
    }

}
