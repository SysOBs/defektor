package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.IjkParamsData;
import pt.uc.sob.defektor.server.model.IjkParams;

import java.util.stream.Collectors;

public class IjkParamMapper {
    public static IjkParams convertToDTO(IjkParamsData ijkParamsData) {
        IjkParams ijkParams = new IjkParams();
        ijkParams.setKey(
                ijkParamsData.getKeyData().stream()
                        .map(KeyMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return ijkParams;
    }

    public static IjkParamsData convertToDAO(IjkParams ijkParams) {
        IjkParamsData ijkParamsData = new IjkParamsData();
        ijkParamsData.setKeyData(
                ijkParams.getKey().stream()
                        .map(KeyMapper::convertToDAO)
                        .collect(Collectors.toList())
        );
        return ijkParamsData;
    }
}
