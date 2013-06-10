/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.impl;

import it.antanicraft.ircintegration.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IrcServerBotService extends SocketConnection implements IrcService {
    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();
    @Override
    public boolean connect() {
        if(!rawConnect()){
            return false;
        }
        rawSend("USER "+ config.getIrcBotName() + " myhost myhost :"+ config.getIrcBotName());
        rawSend("NICK "+ config.getIrcBotName());
        rawSend("JOIN #"+ config.getIrcChannel());
        start();
        return true;
    }

    @Override
    public void disconnect() {
        rawSend("PRIVMSG #"+ config.getIrcChannel()+ " :goodbye.");
        rawDisconnect();
    }

    @Override
    public void sendMessage(String user, String message) {
        rawSend("PRIVMSG #"+ config.getIrcChannel()+ " :" + user + ">"+ message);
    }

    @Override
    public void sendUserLogged(String user, boolean in) {
        String message=new String();
        if(in){
            message=" has logged in.";
        }else{
            message=" has logged out.";
        }
        rawSend("PRIVMSG #"+ config.getIrcChannel()+ " :"+Character.toString((char) 1)+"ACTION reports that <" + user + ">" + message + " "+Character.toString((char) 1));
    }

    @Override
    public void sendMeMessage(String user, String message) {
        rawSend("PRIVMSG #"+ config.getIrcChannel()+ " :"+ Character.toString((char) 1) +"ACTION heard " + user + " " + message +Character.toString((char) 1));
    }

    @Override
    protected void rawReceive(String line) {
        if(line.startsWith("PING")){
            rawSend("PONG "+config.getIrcBotName());
        }else if(line.contains("PRIVMSG")){
            Pattern privPattern=Pattern.compile("^:(.+?)!.+? PRIVMSG #"+config.getIrcChannel()+" :(.+?)$");
            Matcher matcher=privPattern.matcher(line);
            IrcIntegration.logger.info("[MATCH]"+ matcher.matches() );
            if(matcher.matches()){
                ChatEvents.handleMessage(matcher.group(1),matcher.group(2));
            }
        }
    }
}
