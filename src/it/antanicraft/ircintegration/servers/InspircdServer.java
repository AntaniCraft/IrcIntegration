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


public class InspircdServer implements Server{
    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();

    @Override
    public boolean connect(PrintWriter writer, String[] users) {
        writer.println("SERVER " + config.getIrcServName() + " " + config.getIrcAuth() + " 0 " + config.getIrcServName() + " :Just your friendly minecraft server");
        return true;
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
