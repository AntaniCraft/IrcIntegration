/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.io.File;

public class IrcIntegrationConfig extends Configuration {

    private Boolean modEnabled;
    private String ircMode;
    private Boolean ircInspircd;
    private String ircHost;
    private int ircPort;
    private String ircAuth;
    private String ircRevauth;
    private String ircServName;
    private String ircChannel;
    private String ircBotName;


    private static IrcIntegrationConfig instance;

    public static IrcIntegrationConfig getInstance(){
        return instance;
    }

    public IrcIntegrationConfig(File file) {
        super(file);

        getCategory("general").setComment("General mod settings");
        Property pmodEnabled = get("General","enabled",false);
        pmodEnabled.comment = "Enable or disable the mod functionality";
        modEnabled=pmodEnabled.getBoolean(false);

        Property pircMode = get("General","mode","server");
        pircMode.comment = "There are two types of connection to the IRC server:\n\t'server': Preferred." +
                " It connects as an IRC server transparently connecting all players ingame.\n\t" +
                "'serverbot': Connects as a client with bot repeating everything people are saying";
        ircMode = pircMode.getString();

        /*
         *  Quirks category
         */
        getCategory("quirks").setComment("Quirks for specific IRC daemons\nIf all settings are set to false we fall back" +
                "to RFC 1459\n\nThis is ignored for 'serverbot' mode");
        Property ircQuirkInspircd = get("Quirks","inspircd",false);
        ircQuirkInspircd.comment = "Enable quirks for inspircd.";
        ircInspircd=ircQuirkInspircd.getBoolean(false);



        Property pircHost = get("Irc Connection","host","");
        pircHost.comment = "Server host name. It is usually an url";
        ircHost=pircHost.getString();

        Property pircPort = get("Irc Connection","port",6667);
        pircPort.comment = "Connection port. SSL is NOT supported yet.";
        ircPort = pircPort.getInt();

        Property pircAuth = get("Irc Connection","auth","");
        pircAuth.comment = "Password for authenticating on server. Leave it empty in 'serverbot' mode";
        ircAuth = pircAuth.getString();

        Property pircRevAuth = get("Irc Connection","revAuth","");
        pircRevAuth.comment = "Password for authenticating the other server. Leave it empty in 'serverbot' mode";
        ircRevauth = pircRevAuth.getString();

        Property pircName = get("Irc Connection","serverName","localhost");
        pircName.comment = "Server name to pass to the hub server. It's ignored in serverbot mode.";
        ircServName = pircName.getString();

        Property pircBotName = get("Irc Connection","botName","MrSlave");
        pircBotName.comment = "Name of the bot that will appear in the chat.";
        ircBotName=pircBotName.getString();

        Property pircChan = get("Irc Connection","ircChannel","minecraft");
        pircChan.comment = "Name of the channel without the starting #.";
        ircChannel=pircChan.getString();

    }

    public Boolean getModEnabled() {
        return modEnabled;
    }

    public String getIrcMode() {
        return ircMode;
    }

    public Boolean getIrcInspircd() {
        return ircInspircd;
    }

    public String getIrcHost() {
        return ircHost;
    }

    public int getIrcPort() {
        return ircPort;
    }

    public String getIrcAuth() {
        return ircAuth;
    }

    public String getIrcRevauth() {
        return ircRevauth;
    }

    public String getIrcServName() {
        return ircServName;
    }

    public String getIrcChannel() {
        return ircChannel;
    }

    public String getIrcBotName() {
        return ircBotName;
    }
}

