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

import it.antanicraft.ircintegration.IrcIntegrationConfig;



public class P10Server implements Server {

    private IrcIntegrationConfig config = IrcIntegrationConfig.getInstance();






    @Override
    public boolean connect(PrintWriter writer, String[] users) {
        String myname="P10Server";
        Date test= new Date();
        MyBase64 enc= new MyBase64();



        writer.println("SERVER "+ config.getIrcServName()+ " 1 " + test.getTime() + test.getTime() + "J10" + enc.encode(myname.getBytes()) );
        //TODO: Finish this first connect line and go on!



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
