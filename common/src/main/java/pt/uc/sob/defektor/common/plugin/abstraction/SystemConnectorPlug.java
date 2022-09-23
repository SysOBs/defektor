package pt.uc.sob.defektor.common.plugin.abstraction;

import pt.uc.sob.defektor.common.config.SystemConfig;
import pt.uc.sob.defektor.common.data.target_types.TargetType;

import java.util.List;

public abstract class SystemConnectorPlug {

    protected SystemConfig configuration;

    public SystemConnectorPlug(SystemConfig configuration) {
        this.configuration = configuration;
    }

    public abstract void help();

    public abstract void configure();

    public abstract List<TargetType> getTargetTypes();
}
