package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.common.com.data.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected SystemConfig configuration;
//    protected Session session = null;

    public SystemPlug(SystemConfig systemConfiguration) {
        configuration = systemConfiguration;
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    public abstract void configure();
}
