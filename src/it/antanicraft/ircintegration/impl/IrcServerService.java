/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.impl;

import cpw.mods.fml.common.registry.GameRegistry;
import it.antanicraft.ircintegration.IrcIntegration;
import it.antanicraft.ircintegration.IrcService;
import it.antanicraft.ircintegration.SocketConnection;
import net.minecraft.server.MinecraftServer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IrcServerService extends SocketConnection implements IrcService {
    private Pattern pingPattern = Pattern.compile("^:(.+?) PING :(.+?)$");
    private Pattern passPattern = Pattern.compile("^:(.+?) PASS (.+?) .*?$");

    @Override
    protected void rawReceive(String line) {


        Matcher p = pingPattern.matcher(line);
        /* Ugly but works */
        if(p.matches()){
            rawSend("PONG :" + IrcIntegration.irc_servName);
        }else{
            p=passPattern.matcher(line);
            if(p.matches()){
                if(!p.group(2).equals(IrcIntegration.irc_revauth)){
                    rawDisconnect();
                }else{
                    notifyUser(IrcIntegration.irc_botName);
                    addConnectedUsers();
                }
            }else{

            }
        }
    }

    @Override
    public boolean connect() {
        if(!rawConnect()){
            return false;
        }
        start();
        rawSend("PASS " + IrcIntegration.irc_auth);
        rawSend("SERVER "+ IrcIntegration.irc_servName + " 1 :Just your friendly minecraft server.");
        return true;
    }

    private void addConnectedUsers() {
        for ( String user : MinecraftServer.getServer().getAllUsernames()){
            notifyUser(user);
        }
    }

    private void notifyUser(String user) {
        rawSend("NICK "+ user + " :1");
        rawSend(":"+ user + " USER ~" + user +" " + IrcIntegration.irc_servName + " " + IrcIntegration.irc_servName + " :"+user);
        rawSend(":"+ user + " JOIN #" + IrcIntegration.irc_channel);
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void sendMessage(String user, String message) {
          //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendUserLogged(String user, boolean in) {
        if(in){
            rawSend("NICK "+ user + ":1");
            rawSend(":"+ user + " JOIN #" + IrcIntegration.irc_channel);
        }else{

        }
    }

    @Override
    public void sendMeMessage(String user, String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
