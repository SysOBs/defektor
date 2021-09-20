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
//public class KubernetesTest {
//
//    @Test
//    public void applyDeploymentHttpDelay() {
//        new SpringApplication(Defektor.class).run(new String[]{});
//
//        SystemConfigs config = new SystemConfigs(new JSONObject());
//        SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance("kubernetes", config);
//
//        JSONObject paramJson = new JSONObject();
//        IjkParams param = new IjkParams(paramJson);
//
//        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("httpdelay", systemConnectorPlug);
//        injektorPlug.performInjection(param);
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        injektorPlug.stopInjection();
//    }
//
//    @Test
//    public void applyDeploymentHttpAbort() {
//        new SpringApplication(Defektor.class).run(new String[]{});
//
//        SystemConfigs config = new SystemConfigs(new JSONObject());
//        SystemConnectorPlug systemConnectorPlug = (SystemConnectorPlug) SystemConnectorPluginFactory.getInstance().getPluginInstance("kubernetes", config);
//
//        JSONObject paramJson = new JSONObject();
//        IjkParams param = new IjkParams(paramJson);
//
//        InjektorPlug injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance("httpabort", systemConnectorPlug);
//        injektorPlug.performInjection(param);
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        injektorPlug.stopInjection();
//    }
//}
