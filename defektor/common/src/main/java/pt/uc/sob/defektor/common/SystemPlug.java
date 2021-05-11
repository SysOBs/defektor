package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.common.com.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected SystemConfig configuration;

    public SystemPlug(SystemConfig systemConfiguration) {
        configuration = systemConfiguration;
//        this.configure(systemConfiguration);
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    protected abstract void configure(SystemConfig configuration);
}
