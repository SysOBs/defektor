package pt.uc.sob.defektor.common.plugins.system.virtualmachine;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;
import pt.uc.sob.defektor.common.com.sysconfigs.VMConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VMSystemPlug extends SystemPlug {

    private Session session = null;

    public VMSystemPlug(AbstractSysConfig configuration) {
        super(configuration);
    }

    @Override
    public void help() {

    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.PROCESS);
                add(TargetType.INSTANCE);
            }
        };
    }

    @Override
    protected void configure(AbstractSysConfig abstractSysConfig) {
        VMConfig config = (VMConfig) abstractSysConfig;

        JSch jSch = new JSch();

        try {
            jSch.addIdentity(config.getPrivateKey());
            session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            Properties properties = new java.util.Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
