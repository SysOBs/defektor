package pt.uc.sob.defektor.server.control;


import pt.uc.sob.defektor.common.ServerPlug;
import pt.uc.sob.defektor.server.PluginFactory;

public class TaskHandler {

    private ServerPlug plug = null;
    private TaskManager taskManager = new TaskManager();
    private InjektorsManager injektorsManager = new InjektorsManager();

    public TaskHandler(String command) {
        plug = (ServerPlug) PluginFactory.getInstance().getPluginInstance(command, injektorsManager, taskManager);
    }

    public String helloWorld() {
        return plug.helloWorld();
    }
}
