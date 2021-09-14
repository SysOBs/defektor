package pt.uc.sob.defektor.server.campaign.workloadgen;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import pt.uc.sob.defektor.server.api.expection.CampaignException;

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


    private Session getSession() throws CampaignException {
        if (session == null || !session.isConnected()) {
            session = connect();
        }
        return session;
    }

    private Channel getChannel() throws CampaignException {
        if (channel == null || !channel.isConnected()) {
            try {
                channel = (ChannelShell) getSession().openChannel("shell");
                channel.setPty(true);
                channel.connect();

            } catch (Exception e) {
                throw new CampaignException("Error while opening channel: " + e);
            }
        }
        return channel;
    }

    private Session connect() throws CampaignException {

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
            throw new CampaignException("An error occurred while connecting to " + host + ": " + e);
        }

        return session;

    }

    public void executeCommands(List<String> commands) throws CampaignException {

        try {
            Channel channel = getChannel();
            if (channel == null)
                return;

            System.out.println("Sending commands...");
            sendCommands(channel, commands);

            readChannelOutput(channel);
            System.out.println("Finished sending commands!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new CampaignException("An error ocurred during executeCommands: " + e);
        }
    }

    private void sendCommands(Channel channel, List<String> commands) throws CampaignException {

        try {
            PrintStream out = new PrintStream(channel.getOutputStream());

            out.println("#!/bin/bash");
            for (String command : commands) {
                out.println(command);
            }
            out.println("exit");

            out.flush();
        } catch (Exception e) {
            throw new CampaignException("Error while sending commands: " + e);
        }

    }

    private void readChannelOutput(Channel channel) throws CampaignException {

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
            throw new CampaignException("Error while reading channel output: " + e);
        }

    }

    public void close() {
        if(channel != null)
            channel.disconnect();
        if (session != null)
            session.disconnect();
        System.out.println("Disconnected channel and session");
    }
}