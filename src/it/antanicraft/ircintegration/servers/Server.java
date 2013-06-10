/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.servers;


import java.io.PrintWriter;
import java.util.Scanner;

public interface Server {
    /**
     * Sends messages to auth and connect to a remote server
     * @param writer socket ProntWriter
     * @param users users currently connected on the server
     * @return true if succeeded
     */
    public boolean connect(PrintWriter writer,Scanner scanner, String[] users);

    /**
     * Prepares for disconnecting.
     * @param writer
     */
    public void disconnect(PrintWriter writer);

    /**
     * Sends message as user
     * @param writer
     * @param user
     * @param message
     */
    public void sendMessage(PrintWriter writer, String user, String message);

    /**
     * Signals to master server user has logged in/out.
     * @param writer
     * @param user
     * @param in
     */
    public void sendUserLogged(PrintWriter writer, String user, boolean in);

    /**
     * Sends ACTION message as user (useful for deaths)
     * @param writer
     * @param user
     * @param message
     */
    public void sendMeMessage(PrintWriter writer, String user, String message);

    /**
     * Called for every line received
     * @param line
     */
    public void parseLine(String line);
}
