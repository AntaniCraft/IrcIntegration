/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration;

public interface IrcService {
    public boolean connect();
    public void disconnect();

    public void sendMessage(String user, String message);
    public void sendUserLogged(String user, boolean in);

    public void sendMeMessage(String user, String message);

}
