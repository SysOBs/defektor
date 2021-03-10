package pt.uc.sob.defektor.server.control;


import pt.uc.sob.defektor.common.com.PluginInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PluginManager {
    private ServerManager serverManager = new ServerManager();

    public List<PluginInfo> getPluginList() {
        return new ArrayList<PluginInfo>() {{
            add(new PluginInfo("plugin-1.0-SNAPSHOT", new Date(Calendar.getInstance().getTime().getTime()), "url"));
        }} ;
    }
}
