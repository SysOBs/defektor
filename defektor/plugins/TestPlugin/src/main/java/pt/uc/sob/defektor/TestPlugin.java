package pt.uc.sob.defektor;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;

public class TestPlugin extends Plugin {

    public TestPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("pt.uc.sob.defektor.plugins.TestPlugin.start()");

        if (RuntimeMode.DEVELOPMENT.equals(wrapper.getRuntimeMode())) {
            System.out.println("pt.uc.sob.defektor.plugins.TestPlugin");
        }
    }

    @Override
    public void stop() {
        System.out.println("pt.uc.sob.defektor.plugins.TestPlugin.stop()");
    }

    //@Extension
    //public static class WelcomeGreeting implements Greeting {
    //
    //    @Override
    //    public String getGreeting() {
    //        return "Welcome";
    //    }
    //}
}
