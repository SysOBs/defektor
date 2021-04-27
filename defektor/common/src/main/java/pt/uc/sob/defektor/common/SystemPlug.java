package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.SystemConfiguration;
import pt.uc.sob.defektor.common.com.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected SystemConfiguration configuration;

    public SystemPlug(SystemConfiguration systemConfiguration) {
        this.configuration = systemConfiguration;
        this.configure(systemConfiguration);
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    protected abstract void configure(SystemConfiguration configuration);
}
