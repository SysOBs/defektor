package pt.uc.sob.defektor.server.campaign.workloadgen;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.server.api.data.SlaveData;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

public class SSHConnectionManager {

    private Session session;
    private ChannelShell channel;
    private final String host;
    private final String username;
    private final String privateKey;
    private final Integer port;

    public SSHConnectionManager(String host, String username, String privateKey, Integer port) {
        this.host = host;
        this.username = username;
        this.privateKey = privateKey;
        this.port = port;
    }


    private Session getSession() {
        if (session == null || !session.isConnected()) {
            session = connect();
        }
        return session;
    }

    private Channel getChannel() {
        if (channel == null || !channel.isConnected()) {
            try {
                channel = (ChannelShell) getSession().openChannel("shell");
                channel.setPty(true);
                channel.connect();

            } catch (Exception e) {
                System.out.println("Error while opening channel: " + e);
            }
        }
        return channel;
    }

    private Session connect() {

        JSch jSch = new JSch();

        try {

            jSch.addIdentity(privateKey);
            session = jSch.getSession(username, host, port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig(config);
            System.out.println("Connecting SSH to " + host + " - Please wait for few seconds... ");
            session.connect();
            System.out.println("Connected!");
        } catch (Exception e) {
            System.out.println("An error occurred while connecting to " + host + ": " + e);
        }

        return session;

    }

    public void executeCommands(List<String> commands) {

        try {
            Channel channel = getChannel();

            System.out.println("Sending commands...");
            sendCommands(channel, commands);

            readChannelOutput(channel);
            System.out.println("Finished sending commands!");

        } catch (Exception e) {
            System.out.println("An error ocurred during executeCommands: " + e);
        }
    }

    private void sendCommands(Channel channel, List<String> commands) {

        try {
            PrintStream out = new PrintStream(channel.getOutputStream());

            out.println("#!/bin/bash");
            for (String command : commands) {
                out.println(command);
            }
            out.println("exit");

            out.flush();
        } catch (Exception e) {
            System.out.println("Error while sending commands: " + e);
        }

    }

    private void readChannelOutput(Channel channel) {

        byte[] buffer = new byte[1024];

        try {
            InputStream in = channel.getInputStream();
            String line = "";
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(buffer, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    line = new String(buffer, 0, i);
                    System.out.println(line);
                }

                if (line.contains("logout")) {
                    break;
                }

                if (channel.isClosed()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
        } catch (Exception e) {
            System.out.println("Error while reading channel output: " + e);
        }

    }

    public void close() {
        channel.disconnect();
        session.disconnect();
        System.out.println("Disconnected channel and session");
    }
}