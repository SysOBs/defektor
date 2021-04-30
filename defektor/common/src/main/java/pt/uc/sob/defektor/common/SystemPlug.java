package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.sysconfigs.SysConfigInterface;
import pt.uc.sob.defektor.common.com.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected SysConfigInterface configuration;

    public SystemPlug(SysConfigInterface systemConfiguration) {
        this.configuration = systemConfiguration;
        this.configure(systemConfiguration);
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    protected abstract void configure(SysConfigInterface configuration);
}
