//package pt.uc.sob.defektor.server.api.controller;
//
//import org.json.JSONObject;
//import org.junit.Test;
//import org.springframework.boot.SpringApplication;
//import pt.uc.sob.defektor.common.InjektorPlug;
//import pt.uc.sob.defektor.common.SystemConnectorPlug;
//import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
//import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
//import pt.uc.sob.defektor.server.Defektor;
//import pt.uc.sob.defektor.server.pluginization.factories.IjkPluginFactory;
//import pt.uc.sob.defektor.server.pluginization.factories.SystemConnectorPluginFactory;
//
//public class TestController {
//
//    @Test
//    public void instanceReboot() {
//        new SpringApplication(Defektor.class).run(new String[]{});
//
//        JSONObject configJson = new JSONObject();
//        configJson.put("username", "goncalo");
//        configJson.put("host", "192.168.1.2");
//        configJson.put("port", 22);
//        configJson.put("privateKey", "~/.ssh/id_rsa");
//
//        SystemConfigs config = new SystemConfigs(configJson);
//        SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance("virtualmachine", config);
//
//        JSONObject paramJson = new JSONObject();
//        paramJson.put("interval", "10");
//
//        IjkParams param = new IjkParams(paramJson);
//
//        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("instancereboot", systemConnectorPlug);
//        injektorPlug.performInjection(param);
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        injektorPlug.stopInjection();
//    }
//}
