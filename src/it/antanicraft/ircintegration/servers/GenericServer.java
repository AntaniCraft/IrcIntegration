/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.servers;

import java.io.PrintWriter;

/**
 * This class is the protocol respecting RFC 1459
 */
public class GenericServer implements Server {

    @Override
    public boolean connect(PrintWriter writer, String[] users) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
