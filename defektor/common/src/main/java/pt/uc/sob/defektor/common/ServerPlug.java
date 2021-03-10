package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

public abstract class ServerPlug {

    protected InjektorsManagerInterface injektorsManagerInterface;
    protected TaskManagerInterface taskManagerInterface;

    public ServerPlug(InjektorsManagerInterface injektorsManagerInterface, TaskManagerInterface taskManagerInterface) {
        this.injektorsManagerInterface = injektorsManagerInterface;
        this.taskManagerInterface = taskManagerInterface;
    }

    abstract public String helloWorld();

    abstract public String byeWorld();

}
