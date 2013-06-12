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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;


public class InspircdServer implements Server{
    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();

    /* Generic Patterns */
    // :<source> PING <source> :<destination>
    private Pattern pingPattern = Pattern.compile(":(.+?) PING (.+?) (.+?)$");
    private Pattern pongPattern = Pattern.compile(":(.+?) PONG (.+?) (.+?)$");

    /* Auth paterns */
    private Pattern serverAuth = Pattern.compile("SERVER (.+?) (.+?) 0 (.+?) :(.+?)");

    /* Burst phase patterns */

    // :<sid of new users server> UID <uid> <timestamp> <nick> <hostname> <displayed-hostname> <ident> <ip> <signon time> +<modes {mode params}> :<gecos>
    private Pattern uidPattern = Pattern.compile(":(.+?) UID (.+?) (.+?) (.+?) (.+?) (.+?) (.+?) (.+?) (.+?) \\+(.+?) (.+?)? :.+?$");
    // :<sid> FJOIN <channel> <timestamp> +[<modes> {mode params}] [:<[statusmodes],uuid> {<[statusmodes],uuid>}]
    private Pattern fjoinPattern = Pattern.compile(":(.+?) FJOIN (.+?) (.+?) \\+(.+?) :(.+?)$");



    private String remoteServer;

    private HashMap<String,String> remoteUsers=new HashMap<String, String>();
    private ArrayList<String> channelUsers=new ArrayList<String>();

    private HashMap<String,String> localUsers=new HashMap<String, String>();


    @Override
    public boolean connect(PrintWriter writer, Scanner scanner, String[] users) {
        //TODO parse
        /* Send capabilities */
        writer.println("CAPAB START");
        writer.println("CAPAB CAPABILITIES :NICKMAX=32 CHANMAX=65 MAXMODES=20 IDENTMAX=12 MAXQUIT=255");
        writer.println("CAPAB CAPABILITIES :MAXTOPIC=307 MAXKICK=255 MAXGECOS=128 MAXAWAY=200 PROTOCOL=1202");
        writer.println("CAPAB END");
        writer.println("SERVER "+ config.getIrcServName() +" "+config.getIrcAuth()+" 0 " + config.getIrcSID() + " :Your friendly minecraft server");
        writer.flush();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.contains("ERROR")){
                return false;
            }
            Matcher m = serverAuth.matcher(line);
            if(m.matches() && m.group(2).equals(config.getIrcRevauth())){
                Date d=new Date();
                writer.println(":"+config.getIrcSID()+" BURST "+ d.getTime());
                writer.flush();
                while (scanner.hasNextLine()) {
                    String mline=scanner.nextLine();
                    Matcher mm = pingPattern.matcher(line);
                    if(mm.matches()){
                        writer.println("PONG "+mm.group(3)+" "+mm.group(2));
                        continue;
                    }
                    mm=uidPattern.matcher(line);
                    if(mm.matches()){
                        remoteUsers.put(mm.group(2),mm.group(4));
                        continue;
                    }
                    mm=fjoinPattern.matcher(line);
                    if(mm.matches() && mm.group(2).equals("#"+config.getIrcChannel())){
                        String rsusers=mm.group(5);
                        String[] splat=rsusers.split(" ");
                        Pattern usr=Pattern.compile("(.*?),(.+?)");
                        for(String u: splat){
                            Matcher mmm = usr.matcher(u);
                            channelUsers.add(mmm.group(2));
                        }
                    }
                }
                for(String luser : users){
                    int myId=666; //A random numer. Sony style

                }
                writer.println(":"+config.getIrcSID()+" ENDBURST");
                writer.flush();
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
