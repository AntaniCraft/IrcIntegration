/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.impl;

import it.antanicraft.ircintegration.ChatEvents;
import it.antanicraft.ircintegration.IrcIntegration;
import it.antanicraft.ircintegration.IrcService;
import it.antanicraft.ircintegration.SocketConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IrcServerBotService extends SocketConnection implements IrcService {
    @Override
    public boolean connect() {
        if(!rawConnect()){
            return false;
        }
        rawSend("USER "+ IrcIntegration.irc_botName + " myhost myhost :"+ IrcIntegration.irc_botName);
        rawSend("NICK "+ IrcIntegration.irc_botName);
        rawSend("JOIN #"+ IrcIntegration.irc_channel);
        start();
        return true;
    }

    @Override
    public void disconnect() {
        rawSend("PRIVMSG #"+ IrcIntegration.irc_channel+ " :goodbye.");
        rawDisconnect();
    }

    @Override
    public void sendMessage(String user, String message) {
        rawSend("PRIVMSG #"+ IrcIntegration.irc_channel+ " :" + user + ">"+ message);
    }

    @Override
    public void sendUserLogged(String user, boolean in) {
        String message=new String();
        if(in){
            message=" has logged in.";
        }else{
            message=" has logged out.";
        }
        rawSend("PRIVMSG #"+ IrcIntegration.irc_channel+ " :"+Character.toString((char) 1)+"ACTION reports that <" + user + ">" + message + " "+Character.toString((char) 1));
    }

    @Override
    public void sendMeMessage(String user, String message) {
        rawSend("PRIVMSG #"+ IrcIntegration.irc_channel+ " :"+ Character.toString((char) 1) +"ACTION heard " + user + " " + message +Character.toString((char) 1));
    }

    @Override
    protected void rawReceive(String line) {
        if(line.startsWith("PING")){
            rawSend("PONG "+IrcIntegration.irc_botName);
        }else if(line.contains("PRIVMSG")){
            Pattern privPattern=Pattern.compile("^:(.+?)!.+? PRIVMSG #"+IrcIntegration.irc_channel+" :(.+?)$");
            Matcher matcher=privPattern.matcher(line);
            IrcIntegration.logger.info("[MATCH]"+ matcher.matches() );
            if(matcher.matches()){
                ChatEvents.handleMessage(matcher.group(1),matcher.group(2));
            }
        }
    }
}
