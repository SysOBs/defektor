package pt.uc.sob.defektor.server.api.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;
import pt.uc.sob.defektor.server.Defektor;
import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;

public class TestController {

    @Test
    public void instanceReboot() {
        new SpringApplication(Defektor.class).run(new String[]{});

        JSONObject configJson = new JSONObject();
        configJson.put("username", "goncalo");
        configJson.put("host", "192.168.1.2");
        configJson.put("port", 22);
        configJson.put("privateKey", "~/.ssh/id_rsa");

        SystemConfig config = new SystemConfig(configJson);
        SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance("virtualmachine", config);

        JSONObject paramJson = new JSONObject();
        paramJson.put("interval", "10");

        IjkParam param = new IjkParam(paramJson);

        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("instancereboot", systemConnectorPlug);
        injektorPlug.performInjection(param);
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        injektorPlug.stopInjection();
    }

    @Test
    public void performInjection1() {
        //TODO Mudar as config classes para dentro dos plugins
//        IjkTaskHandler ijkTaskHandler = new IjkTaskHandler("process-terminator", systemPlug);
    }
}