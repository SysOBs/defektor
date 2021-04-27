package pt.uc.sob.defektor.common.plugins;


import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPluginInstaller {
    protected AbstractPluginFactory pluginFactory;

    public AbstractPluginInstaller(AbstractPluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    protected abstract String getPluginDirectory();

    protected List<String> getPluginList() {
        return Stream.of(new File(getPluginDirectory()).listFiles())
                .filter(file -> !file.isDirectory())
                .map(f -> getPluginDirectory() + File.separator + f.getName())
                .filter(f -> f.endsWith(".jar"))
                .collect(Collectors.toList());
    }

    public void installPlugins() {
        pluginFactory.unload();
        pluginFactory.loadPlugins(getPluginList());
    }
}
