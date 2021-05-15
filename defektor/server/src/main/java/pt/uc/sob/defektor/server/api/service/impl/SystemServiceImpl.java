package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.mapper.SystemConfigMapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.model.Plan;
import pt.uc.sob.defektor.server.model.SystemConfig;
import pt.uc.sob.defektor.server.utils.Strings;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final DefektorRepository defektorRepository;
    private final String dbFileDir = Strings.SYS_CONFIG_DB_PATH;

    @Override
    public SystemConfig sysConfigAdd(SystemConfig config) throws DuplicateEntryException {
        defektorRepository.save(SystemConfigMapper.convertToDAO(config), dbFileDir);
        return config;
    }

    @Override
    public List<SystemConfigData> sysConfigListDAO() {
        return (List<SystemConfigData>) defektorRepository.findAll(dbFileDir);
    }

    @Override
    public List<SystemConfig> sysConfigList() {
        return (List<SystemConfig>) defektorRepository.findAll(dbFileDir).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
