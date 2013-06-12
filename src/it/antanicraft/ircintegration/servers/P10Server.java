/**
 * Copyright (c) KevinGrave6, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.servers;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.dedicated.DedicatedServer;

import it.antanicraft.ircintegration.IrcIntegrationConfig;


public class P10Server implements Server {

    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();
    private Pattern serverAuth = Pattern.compile("PASS : (.+?)");
    private Pattern serverAuth2 = Pattern.compile("SERVER (.+?) (\\d+?) (\\d+?) (\\d+?) (J10) (.+?) ((\\+).*?) :(.*?)");
    private MyBase64 enc= new MyBase64();
    private String connServerName;
    private String connServerNumeric;
    private int myserverNumeric =config.getIrcSID();
    private String myserverNumeric64=enc.encode((Util.getSID()+String.valueOf(DedicatedServer.getServer().getMaxPlayers())).getBytes());
    //TODO:when it comes to max players (P10 allow max players as a number equal to "power of 2 less one", need to check this out







    @Override
    public boolean connect(PrintWriter writer, Scanner scanner, String[] users) {
        String  instance=String.valueOf(new Date().getTime());
        int firstTryConnect=1;
        int serverMaxN= DedicatedServer.getServer().getMaxPlayers();
        String protocolInit="J10";
        String sN64=enc.encode(String.valueOf(myserverNumeric).getBytes()) ;
        String numeric64= sN64.concat(enc.encode(String.valueOf(serverMaxN).getBytes()));

        writer.println("PASS :" + config.getIrcAuth());
        writer.println("SERVER "+ config.getIrcServName()+ String.valueOf(firstTryConnect) + instance + String.valueOf(new Date().getTime()) + protocolInit + numeric64 + "+" + ":"+"["+config.getIrcHost()+"] "+":Your friendly minecraft server ");

        writer.flush();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.contains("ERROR")){
                return false;
            }
            Matcher m = serverAuth.matcher(line);
            if(m.matches() && m.group(3).equals(config.getIrcRevauth())){
                line=scanner.nextLine();
                m=serverAuth2.matcher(line);
                if(m.group(1).equals("SERVER") && m.matches()){
                 connServerName =m.group(2);
                 connServerNumeric =m.group(7);
              //TODO: end this (users list exchange method + check all the above m.group()'s ids

                return true;
                }

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

    private class MyBase64 {

        private final  char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

        private  int[]  toInt   = new int[128];

         {
            for(int i=0; i< ALPHABET.length; i++){
                toInt[ALPHABET[i]]= i;
            }
        }

        /**
         * Translates the specified byte array into Base64 string.
         *
         * @param buf the byte array (not null)
         * @return the translated Base64 string (not null)
         */
        public  String encode(byte[] buf){
            int size = buf.length;
            char[] ar = new char[((size + 2) / 3) * 4];
            int a = 0;
            int i=0;
            while(i < size){
                byte b0 = buf[i++];
                byte b1 = (i < size) ? buf[i++] : 0;
                byte b2 = (i < size) ? buf[i++] : 0;

                int mask = 0x3F;
                ar[a++] = ALPHABET[(b0 >> 2) & mask];
                ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
                ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
                ar[a++] = ALPHABET[b2 & mask];
            }
            switch(size % 3){
                case 1: ar[--a]  = '=';
                case 2: ar[--a]  = '=';
            }
            return new String(ar);
        }

        /**
         * Translates the specified Base64 string into a byte array.
         *
         * @param s the Base64 string (not null)
         * @return the byte array (not null)
         */
        public  byte[] decode(String s){
            int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
            byte[] buffer = new byte[s.length()*3/4 - delta];
            int mask = 0xFF;
            int index = 0;
            for(int i=0; i< s.length(); i+=4){
                int c0 = toInt[s.charAt( i )];
                int c1 = toInt[s.charAt( i + 1)];
                buffer[index++]= (byte)(((c0 << 2) | (c1 >> 4)) & mask);
                if(index >= buffer.length){
                    return buffer;
                }
                int c2 = toInt[s.charAt( i + 2)];
                buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);
                if(index >= buffer.length){
                    return buffer;
                }
                int c3 = toInt[s.charAt( i + 3 )];
                buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
            }
            return buffer;
        }

    }



}
