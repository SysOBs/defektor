package pt.uc.sob.defektor.server.pluginization.control;


import pt.uc.sob.defektor.common.com.info.InjektorInfo;
import pt.uc.sob.defektor.common.com.info.PluginInfo;
import pt.uc.sob.defektor.common.com.info.SystemInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginManager {

    private List<PluginInfo> getSystemPluginList() {
        List<PluginInfo> pluginInfoList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("plugins" + File.separator + "libs" + File.separator + "system"))) {

            List<String> resultList = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString().split(".jar")[0]).collect(Collectors.toList());

            for(String result : resultList) {
                pluginInfoList.add(
                        new SystemInfo(result)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pluginInfoList;
    }

    private List<PluginInfo> getIjkPluginList() {
        List<PluginInfo> pluginInfoList = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("plugins" + File.separator + "libs" + File.separator + "ijk"))) {

            List<String> resultList = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString().split(".jar")[0]).collect(Collectors.toList());

            for(String result : resultList) {
                pluginInfoList.add(
                        new InjektorInfo(result)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pluginInfoList;
    }

    public List<PluginInfo> getPluginList(String type) {
        if(type.equals("SYSTEM"))
            return getSystemPluginList();
        else
            return getIjkPluginList();
    }
}
