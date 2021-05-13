package pt.uc.sob.defektor.common;

import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.common.com.TargetType;

import java.util.List;

public abstract class SystemPlug {

    protected SystemConfig configuration;
//    protected Session session = null;

    public SystemPlug(SystemConfig systemConfiguration) {
        configuration = systemConfiguration;
        this.configure(systemConfiguration);
    }

    public abstract void help();

    public abstract List<TargetType> getTargetTypes();

    public abstract void configure(SystemConfig configuration);
}
