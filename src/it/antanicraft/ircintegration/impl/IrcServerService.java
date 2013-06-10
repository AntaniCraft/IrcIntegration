/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.impl;

import it.antanicraft.ircintegration.IrcIntegration;
import it.antanicraft.ircintegration.IrcIntegrationConfig;
import it.antanicraft.ircintegration.IrcService;
import it.antanicraft.ircintegration.SocketConnection;
import it.antanicraft.ircintegration.servers.InspircdServer;
import it.antanicraft.ircintegration.servers.P10Server;
import it.antanicraft.ircintegration.servers.Server;
import net.minecraft.server.MinecraftServer;


public class IrcServerService extends SocketConnection implements IrcService {

    private Server server;

    public IrcServerService() {
        if(IrcIntegrationConfig.getInstance().getIrcInspircd()){
            server=new InspircdServer();
        }else{
            server=new P10Server();
        }
        //TODO Other backends
    }

    @Override
    protected void rawReceive(String line) {
        server.parseLine(line);
    }

    @Override
    public boolean connect() {
        if(rawConnect() && server.connect(getWriter(),MinecraftServer.getServer().getAllUsernames())){
            start();
            return true;
        }
        rawDisconnect();
        return false;
    }

    @Override
    public void disconnect() {
        server.disconnect(getWriter());
    }

    @Override
    public void sendMessage(String user, String message) {
          server.sendMessage(getWriter(),user,message);
    }

    @Override
    public void sendUserLogged(String user, boolean in) {
          server.sendUserLogged(getWriter(),user,in);
    }

    @Override
    public void sendMeMessage(String user, String message) {
        server.sendMeMessage(getWriter(),user,message);
    }

}
