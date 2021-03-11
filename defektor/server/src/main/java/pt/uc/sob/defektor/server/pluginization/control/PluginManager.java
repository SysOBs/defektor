package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.com.PluginInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginManager {
    private ServerManager serverManager = new ServerManager();

    public List<PluginInfo> getPluginList() {
        List<PluginInfo> pluginInfoList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("plugins" + File.separator + "libs"))) {

            List<String> resultList = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            for(String result : resultList)
                pluginInfoList.add(
                        new PluginInfo(result, new Date(Calendar.getInstance().getTime().getTime()))
                );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pluginInfoList;
    }
}
