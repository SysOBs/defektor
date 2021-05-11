package pt.uc.sob.defektor.server.api.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.Defektor;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.SystemPluginFactory;

public class TestController {

    @Test
    public void performInjection() {
        new SpringApplication(Defektor.class).run(new String[]{});

        JSONObject configJson = new JSONObject();
        configJson.put("username", "goncalo");
        configJson.put("host", "192.168.1.2");
        configJson.put("port", 22);
        configJson.put("privateKey", "~/.ssh/id_rsa");

        SystemConfig config = new SystemConfig(configJson);
        SystemPlug systemPlug = (SystemPlug) SystemPluginFactory.getInstance().getPluginInstance("virtualmachine", config);

        JSONObject paramJson = new JSONObject();
        IjkParam param = new IjkParam(paramJson);

        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("instancereboot", systemPlug);
        injektorPlug.performInjection(param);
    }

    @Test
    public void performInjection1() {
        //TODO Mudar as config classes para dentro dos plugins
//        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator", systemPlug);
    }
}
