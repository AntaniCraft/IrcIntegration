/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration.servers;

import it.antanicraft.ircintegration.IrcIntegrationConfig;

public class Util {
    private static int counter = 0;
    private static String sid=new String();

    public static String getSID(){
        if(!sid.isEmpty()){
            return sid;
        }
        int sid=0;
        char[] name=IrcIntegrationConfig.getInstance().getIrcServName().toCharArray();
        for(char c:name){
            sid=5*sid+(int) c;
        }
        return String.format("%03d",sid % 1000);
    }
    public static String generateUID(){
        if(counter==Integer.MAX_VALUE){
            counter=0;
        }
        return String.format("%010x",++counter);
    }
}
