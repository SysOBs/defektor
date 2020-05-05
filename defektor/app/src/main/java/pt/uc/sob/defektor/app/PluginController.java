package pt.uc.sob.defektor.app;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;

import java.util.List;

/**
 * PluginController contains all methods to control plugins.
 */
public class PluginController {

    private final PluginManager pluginManager;

    /**
     * Constructor.
     */
    public PluginController() {
        this.pluginManager = new DefaultPluginManager() {
        };

        System.out.println("Plugins root path: " + this.pluginManager.getPluginsRoot());
    }

    /**
     * Loads all plugins.
     */
    public void load() {
        this.pluginManager.loadPlugins();
    }

    /**
     * Starts all plugins or one specific plugin.
     *
     * @param pluginName The plugin name.
     */
    public void start(String pluginName) {
        if (pluginName != null)
            this.pluginManager.startPlugin(pluginName);
        else
            this.pluginManager.startPlugins();
    }

    /**
     * Gets loaded plugins list.
     *
     * @return List of plugins loaded into the plugin manager.
     */
    public List<PluginWrapper> getPluginsList() {
        return this.pluginManager.getPlugins();
    }

    /**
     * Stops all plugins or one specific plugin.
     *
     * @param pluginName The plugin name.
     */
    public void stop(String pluginName) {
        if (pluginName != null)
            this.pluginManager.stopPlugin(pluginName);
        else
            this.pluginManager.stopPlugins();
    }
}
