package pt.uc.sob.defektor.common.plugins.ijk.instancereboot;

import com.jcraft.jsch.*;
import pt.uc.sob.defektor.common.InjektorPlug;
import pt.uc.sob.defektor.common.com.Target;
import pt.uc.sob.defektor.common.com.TargetType;
import pt.uc.sob.defektor.common.com.params.AbstractParam;
import pt.uc.sob.defektor.common.com.params.InstanceRebootParam;
import pt.uc.sob.defektor.common.plugins.interfaces.InjektorsManagerInterface;
import pt.uc.sob.defektor.common.plugins.interfaces.TaskManagerInterface;

import java.util.ArrayList;
import java.util.List;

public class InstanceReboot extends InjektorPlug {

    public InstanceReboot(InjektorsManagerInterface injektorsManagerInterface, TaskManagerInterface taskManagerInterface) {
        super(injektorsManagerInterface, taskManagerInterface);
    }

    @Override
    public void performInjection(AbstractParam abstractParam) {
        InstanceRebootParam param = (InstanceRebootParam) abstractParam;

        JSch jSch = new JSch();
        Session session = null;

        try {
            jSch.addIdentity(param.getKeyDir());
            session = jSch.getSession(param.getUsername(), param.getHost(), param.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        String command = "sudo reboot";
        try {
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            ((ChannelExec) channel).setPty(false);
            channel.connect();
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            throw new RuntimeException("Error during SSH command execution. Command: " + command);
        }
    }

    @Override
    public void stopInjection() {

    }

    @Override
    public void setup() {

    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.POD);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

    @Override
    public Class getTheNameOfTheClass() {
        return null;
    }

}
