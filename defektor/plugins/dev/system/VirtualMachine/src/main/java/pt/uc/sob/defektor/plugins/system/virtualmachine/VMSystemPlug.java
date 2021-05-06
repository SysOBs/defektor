package pt.uc.sob.defektor.plugins.system.virtualmachine;

import com.jcraft.jsch.*;
import pt.uc.sob.defektor.common.SystemPlug;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.sysconfigs.AbstractSysConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//TODO refactor package names (not "commmon")
public class VMSystemPlug extends SystemPlug {

    private Session session = null;

    public VMSystemPlug(AbstractSysConfig configuration) {
        super(configuration);
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
    protected void configure(AbstractSysConfig abstractSysConfig) {
        Object[] objects = abstractSysConfig.getObjects();
        Config config = new Config(
                (String) objects[0],
                (String) objects[1],
                (Integer) objects[2],
                (String) objects[3]
        );
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
        if(this.session == null) throw new RuntimeException("session not defined");

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