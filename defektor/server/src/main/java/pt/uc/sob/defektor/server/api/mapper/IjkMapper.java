package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.IjkData;
import pt.uc.sob.defektor.server.model.Ijk;

public class IjkMapper {

    public static Ijk convertToDTO(IjkData ijkData) {
        Ijk ijk = new Ijk();
        ijk.setName(ijkData.getName());
        ijk.setParams(IjkParamMapper.convertToDTO(ijkData.getParams()));

        return ijk;
    }

    public static IjkData convertToDAO(Ijk ijk) {
        IjkData ijkData = new IjkData();
        ijkData.setName(ijk.getName());
        ijkData.setParams(IjkParamMapper.convertToDAO(ijk.getParams()));

        return ijkData;
    }
}
