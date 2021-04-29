package pt.uc.sob.defektor.common.plugins.system.virtualmachine;

import com.jcraft.jsch.*;
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
        configure(configuration);
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

    public void sendSshCommand(String command) {
        if(this.session == null) return;

        ChannelExec channel = null;
        try {
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setPty(false);
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }
    }
}
