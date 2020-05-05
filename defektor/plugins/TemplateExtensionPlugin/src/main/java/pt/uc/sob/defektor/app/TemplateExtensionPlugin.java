package pt.uc.sob.defektor.app;

import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;
import pt.uc.sob.defektor.app.api.HoleyBoat;
import pt.uc.sob.defektor.app.api.InjektorConfig;

public class TemplateExtensionPlugin extends Plugin {

    public TemplateExtensionPlugin(PluginWrapper wrapper) {
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

    @Extension
    public static class HoleyBoatImpl implements HoleyBoat {

        @Override
        public void start(InjektorConfig injectionConfig) {

        }
    }
}
