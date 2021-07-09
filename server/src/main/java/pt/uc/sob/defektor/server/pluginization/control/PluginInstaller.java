package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.server.pluginization.AbstractPluginFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginInstaller {

    private final String pluginPath;
    protected AbstractPluginFactory pluginFactory;


    public PluginInstaller(AbstractPluginFactory pluginFactory, String pluginPath) {
        this.pluginFactory = pluginFactory;
        this.pluginPath = pluginPath;
    }

    protected List<String> getPluginList() {
        File[] fileList = new File(this.pluginPath).listFiles();
        if(fileList == null) return null;

        return Stream.of(fileList)
                .filter(file -> !file.isDirectory())
                .map(f -> this.pluginPath + File.separator + f.getName())
                .filter(f -> f.endsWith(".jar"))
                .collect(Collectors.toList());
    }

    public void installPlugins() {
        pluginFactory.unload();
        pluginFactory.loadPlugins(getPluginList());
    }
}
