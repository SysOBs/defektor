package pt.uc.sob.defektor.server;

import lombok.SneakyThrows;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParam;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.pluginization.IjkPluginFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InjektionController extends InjektionData {

    private InjektorPlug injektorPlug = null;
    private final List<SystemPlug> systemPlugs;

    public InjektionController(IjkData ijk, WorkLoadData workLoad, TargetData target, List<SystemPlug> systemPlugs) {
        super(ijk, workLoad, target);
        this.systemPlugs = systemPlugs;
    }

    public void performInjektion() {
        systemPlugs.forEach(systemPlug -> {
            injektorPlug = (InjektorPlug) IjkPluginFactory.getInstance().getPluginInstance(this.getIjk().getName(), systemPlug);
            new Thread(
                    () -> {
                        injektorPlug.performInjection(buildIjkParams(this.getIjk().getParams()));
                        sleep(getWorkLoad().getDuration());
                        stopInjektion();
                    }
            ).start();
        });
    }

    public void stopInjektion() {
        injektorPlug.stopInjection();
    }

    private IjkParam buildIjkParams(List<KeyValueData> params) {
        JSONObject jsonObject = new JSONObject();
        for (KeyValueData keyValue : params) {
            jsonObject.put(keyValue.getKey(), keyValue.getValue());
        }
        return new IjkParam(jsonObject);
    }

    @SneakyThrows
    private void sleep(int seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    public void applyWorkload() {

    }

    public void startCampaign() {

    }
}
