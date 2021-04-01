package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.SystemConfiguration;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.plugins.interfaces.SystemsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.util.List;

public abstract class SystemPlug {

    protected SystemsManagerInterface systemsManagerInterface;
    protected TaskManagerInterface taskManagerInterface;
    protected SystemConfiguration configuration;

    public SystemPlug(SystemsManagerInterface systemsManagerInterface, TaskManagerInterface taskManagerInterface) {
        this.systemsManagerInterface = systemsManagerInterface;
        this.taskManagerInterface = taskManagerInterface;
    }

    public abstract void help();

    public abstract void setup();

    public abstract List<TargetType> getTargetTypes();

    public void configure(SystemConfiguration configuration) {
        this.configuration = configuration;
    }
}
