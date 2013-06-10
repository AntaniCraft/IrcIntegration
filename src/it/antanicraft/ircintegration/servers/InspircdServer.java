/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.servers;

import it.antanicraft.ircintegration.IrcIntegration;
import it.antanicraft.ircintegration.IrcIntegrationConfig;
import it.antanicraft.ircintegration.IrcService;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;


public class InspircdServer implements Server{
    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();

    private Pattern serverAuth = Pattern.compile("SERVER (.+?) (.+?) 0 (.+?) :(.+?)");

    private String remoteServer;

    @Override
    public boolean connect(PrintWriter writer, Scanner scanner, String[] users) {
        //TODO parse
        /* Send capabilities */
        writer.println("CAPAB START");
        writer.println("CAPAB CAPABILITIES :NICKMAX=32 CHANMAX=65 MAXMODES=20 IDENTMAX=12 MAXQUIT=255");
        writer.println("CAPAB CAPABILITIES :MAXTOPIC=307 MAXKICK=255 MAXGECOS=128 MAXAWAY=200 PROTOCOL=1202");
        writer.println("CAPAB END");
        writer.println("SERVER "+ config.getIrcServName() +" "+config.getIrcAuth()+" 0 " + config.getIrcServName() + " :Your friendly minecraft server");
        writer.flush();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.contains("ERROR")){
                return false;
            }
            Matcher m = serverAuth.matcher(line);
            if(m.matches() && m.group(2).equals(config.getIrcRevauth())){

                return true;
            }
        }
        return false;
    }

    @Override
    public void disconnect(PrintWriter writer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendMessage(PrintWriter writer, String user, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendUserLogged(PrintWriter writer, String user, boolean in) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendMeMessage(PrintWriter writer, String user, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void parseLine(String line) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
