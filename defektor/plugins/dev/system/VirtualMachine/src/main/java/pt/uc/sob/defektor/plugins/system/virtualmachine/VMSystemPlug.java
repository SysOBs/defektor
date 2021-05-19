package pt.uc.sob.defektor.plugins.system.virtualmachine;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class VMSystemPlug extends SystemPlug {

    protected Session session = null;

    public VMSystemPlug(SystemConfig configuration) {
        super(configuration);
        configure();
    }

    @Override
    public void help() {
        System.out.println("--> HELP");
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
    public void configure() {
        Config config = Utils.jsonToObject(configuration.getJsonSysConfig().toString());
        JSch jSch = new JSch();

        try {
            jSch.addIdentity(config.getPrivateKey());
            session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void sendSSHCommand(String command) {
        if (this.session == null) throw new RuntimeException("session not defined");
        closeConnectedSession();

        ChannelExec channel = null;
        try {
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setPty(false);
            channel.connect();
        } catch (JSchException e) {
            configure();
            e.printStackTrace();
        } finally {
            closeConnectedSession();
            closeConnectedChannel(channel);
        }
    }

    private void closeConnectedSession() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    private void closeConnectedChannel(ChannelExec channel) {
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
    }
}
