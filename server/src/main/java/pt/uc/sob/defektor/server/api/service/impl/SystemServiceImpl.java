package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SystemService;
import pt.uc.sob.defektor.server.utils.Strings;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final DefektorRepository<SystemConfigData> defektorRepository;
    private final String dbFilePath = Strings.DB.SYS_CONFIG_DB_PATH;

    @Override
    public void sysConfigAdd(SystemConfigData systemConfig) {
        defektorRepository.save(systemConfig, dbFilePath);
    }

    @Override
    public List<SystemConfigData> sysConfigList() {
        return defektorRepository.findAll(dbFilePath);
    }
}
