/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public abstract class SocketConnection extends Thread {
    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printer;


    protected abstract void rawReceive(String line);

    protected void rawSend(String line){
        IrcIntegration.logger.info("[IRCSend]"+line);
        printer.println(line);
        printer.flush();
    }

    protected PrintWriter getWriter(){
        return printer;
    }


    public boolean rawConnect(){
        try {
            socket=new Socket(config.getIrcHost(),config.getIrcPort());
            scanner=new Scanner(socket.getInputStream());
            printer=new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            IrcIntegration.logger.warning("Error while connecting: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean rawDisconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            IrcIntegration.logger.warning("Error while disconnecting: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        while(scanner.hasNext()){
            String line=scanner.nextLine();
            IrcIntegration.logger.info("[IRC]"+line);
            rawReceive(line);
        }
    }
}
