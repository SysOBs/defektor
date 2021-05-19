package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.data.SystemConfigData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.SystemConfig;

import java.util.List;

public interface SystemService {

    void sysConfigAdd(SystemConfigData systemConfig) throws DuplicateEntryException;

    List<SystemConfigData> sysConfigListDAO();

    List<SystemConfigData> sysConfigList();
}
