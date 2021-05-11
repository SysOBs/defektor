package pt.uc.sob.defektor.plugins.system.virtualmachine;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.SystemConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VMSystemPlug extends SystemPlug {

    Session session = null;

    public VMSystemPlug(SystemConfig configuration) {
        super(configuration);
        configure(configuration);
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
    protected void configure(SystemConfig systemConfig) {
        Config config = Utils.jsonToObject(systemConfig.getJsonSysConfig().toString());
        JSch jSch = new JSch();

        try {
            jSch.addIdentity(config.getPrivateKey());
            session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
            System.out.println(session.toString());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            Properties properties = new java.util.Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void sendSSHCommand(String command) {
//        System.out.println(session.getHost() + " " + session.getPort());
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
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
        }
    }
}
