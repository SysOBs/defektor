package pt.uc.sob.defektor.app;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;

public class TemplatePlugin extends Plugin {

    public TemplatePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("TemplatePlugin.start()");

        if (RuntimeMode.DEVELOPMENT.equals(wrapper.getRuntimeMode())) {
            System.out.println("Development mode!");
        }
    }

    @Override
    public void stop() {
        System.out.println("TemplatePlugin.stop()");
    }
}
