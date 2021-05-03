package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.common.com.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected AbstractSysConfig configuration;

    public SystemPlug(AbstractSysConfig systemConfiguration) {
        this.configuration = systemConfiguration;
        this.configure(systemConfiguration);
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    protected abstract void configure(AbstractSysConfig configuration);
}
