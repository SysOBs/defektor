package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.IjkData;
import pt.uc.sob.defektor.server.model.Ijk;

import java.util.stream.Collectors;

public class IjkMapper {

    public static Ijk convertToDTO(IjkData ijkData) {
        Ijk ijk = new Ijk();
        ijk.setName(ijkData.getName());
        ijk.setParams(
                ijkData.getParams().stream()
                        .map(KeyValueMapper::convertToDTO)
                        .collect(Collectors.toList())
        );

        return ijk;
    }

    public static IjkData convertToDAO(Ijk ijk) {
        IjkData ijkData = new IjkData();
        ijkData.setName(ijk.getName());

        ijkData.setParams(
                ijk.getParams().stream()
                        .map(KeyValueMapper::convertToDAO)
                        .collect(Collectors.toList())
        );

        return ijkData;
    }
}
