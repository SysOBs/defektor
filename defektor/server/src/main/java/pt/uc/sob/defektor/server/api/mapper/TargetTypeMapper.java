package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.TargetTypeData;
import pt.uc.sob.defektor.server.model.TargetType;

public abstract class TargetTypeMapper {

    public static TargetType convertToDAO(TargetTypeData targetTypeData) {
        TargetType targetType = new TargetType();
        targetType.setName(targetTypeData.getName());

        return targetType;
    }

    public static TargetTypeData convertToDTO(TargetType targetType) {
        TargetTypeData targetTypeData = new TargetTypeData();
        targetTypeData.setName(targetType.getName());

        return targetTypeData;
    }

    //TODO Check if can switch targettypedata for targettype enum
}
