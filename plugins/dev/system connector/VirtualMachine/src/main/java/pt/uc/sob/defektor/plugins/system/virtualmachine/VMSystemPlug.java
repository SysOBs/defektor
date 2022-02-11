package pt.uc.sob.defektor.plugins.system.virtualmachine;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.common.com.data.target_types.SshEnabledTargetType;
import pt.uc.sob.defektor.common.com.data.target_types.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfigs;
import pt.uc.sob.defektor.common.plugin_interface.SystemConnectorPlug;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class VMSystemPlug extends SystemConnectorPlug {

    protected Session session = null;

    public VMSystemPlug(SystemConfigs configuration) {
        super(configuration);
    }

    @Override
    public void help() {
        System.out.println("--> HELP");
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return List.of(SshEnabledTargetType.values());
    }

    @Override
    public void configure() {
        Configs configs = Utils.jsonToObject(configuration.getJsonSysConfigs().toString());
        JSch jSch = new JSch();

        try {
            jSch.addIdentity(configs.getPrivateKey());
            session = jSch.getSession(configs.getUsername(), configs.getHost(), configs.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void sendSSHCommand(String command) {
        configure();
        if (this.session == null) throw new RuntimeException("session not defined");

        ChannelExec channel = null;
        try {
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setPty(false);
            channel.connect();
        } catch (JSchException e) {
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
