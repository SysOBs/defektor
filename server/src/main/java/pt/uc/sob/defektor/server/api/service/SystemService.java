package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.SystemConfigData;

import java.util.List;

public interface SystemService {

    void sysConfigAdd(SystemConfigData systemConfig);

    List<SystemConfigData> sysConfigList();
}
