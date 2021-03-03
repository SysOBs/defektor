package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.TargetData;
import pt.uc.sob.defektor.server.model.Target;
import pt.uc.sob.defektor.server.model.TargetType;

public abstract class TargetMapper {
    public static Target convertToDAO(TargetData targetData) {
        Target target = new Target();
        target.setName(targetData.getName());
        target.setType(TargetTypeMapper.convertToDAO(targetData.getType()));

        return target;
    }

    public static TargetData convertToDTO(Target target) {

        TargetData targetData = new TargetData();
        targetData.setName(target.getName());
        targetData.setType(TargetTypeMapper.convertToDTO(target.getType()));

        return targetData;
    }
}
