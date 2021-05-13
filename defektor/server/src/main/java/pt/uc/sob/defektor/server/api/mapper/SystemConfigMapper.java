package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.model.SystemConfig;

import java.util.stream.Collectors;

public class SystemConfigMapper {

    public static SystemConfigData convertToDAO(SystemConfig systemConfig) {
        SystemConfigData systemConfigData = new SystemConfigData();
        systemConfigData.setSystemType(SystemTypeMapper.convertToDAO(systemConfig.getSystemType()));
        systemConfigData.setKeyData(
                systemConfig.getKey().stream()
                    .map(KeyMapper::convertToDAO)
                    .collect(Collectors.toList())
        );
        return systemConfigData;
    }

    public static SystemConfig convertToDTO(SystemConfigData systemConfigData) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setSystemType(SystemTypeMapper.convertToDTO(systemConfigData.getSystemType()));
        systemConfig.setKey(
                systemConfigData.getKeyData().stream()
                        .map(KeyMapper::convertToDTO)
                        .collect(Collectors.toList())
        );
        return systemConfig;
    }
}
