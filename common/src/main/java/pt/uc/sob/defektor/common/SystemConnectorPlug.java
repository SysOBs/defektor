package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.com.data.TargetType;

import java.util.List;

public abstract class SystemConnectorPlug {

    protected SystemConfigs configuration;

    public SystemConnectorPlug(SystemConfigs systemConfiguration) {
        configuration = systemConfiguration;
    }

    public abstract void help();

    public abstract void configure();

    public abstract List<TargetType> getTargetTypes();
}
