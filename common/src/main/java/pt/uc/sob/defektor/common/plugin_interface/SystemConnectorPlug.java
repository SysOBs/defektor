package pt.uc.sob.defektor.common.plugin_interface;

import pt.uc.sob.defektor.common.com.data.target_types.TargetType;

import java.util.List;
import java.util.Map;

public abstract class SystemConnectorPlug {

    protected Map<String, String> configuration;

    public SystemConnectorPlug(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public abstract void help();

    public abstract void configure();

    public abstract List<TargetType> getTargetTypes();
}
