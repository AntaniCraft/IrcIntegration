/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import it.antanicraft.ircintegration.impl.IrcClientService;
import it.antanicraft.ircintegration.impl.IrcServerService;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;


import java.io.File;
import java.util.logging.Logger;

@Mod(
        modid="IrcIntegration",
        name="Irc Integration",
        acceptedMinecraftVersions = "1.5.2",
        dependencies = "required-after:Forge"

)
public class IrcIntegration {

    public static Logger logger;

    public static IrcService ircService;

    @Mod.Instance
    public static IrcIntegration instance;

    /* Configuration items */
    public static Boolean mod_enabled;

    /** Irc Mode */
    public static String irc_mode;


    public static Boolean irc_inspircd;

    /** Irc server host  */
    public static String irc_host;

    /** Irc server port */
    public static int irc_port;

    /** Irc server auth */
    public static String irc_auth;

    /** Irc server auth */
    public static String irc_revauth;

    /** Irc server auth */
    public static String irc_servName;

    /** Irc Channel name */
    public static String irc_channel;

    /** Bot name  */
    public static String irc_botName;


    /**
     * Loads configs and ensures that we are running on the server
     * @param evt
     */
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent evt){
        logger=evt.getModLog();
        File configFile = evt.getSuggestedConfigurationFile();
        Configuration config = new Configuration(configFile);

        config.getCategory("General").setComment("General mod settings");
        Property modEnabled = config.get("General","enabled",false);
        modEnabled.comment = "Enable or disable the mod functionality";
        mod_enabled=modEnabled.getBoolean(false);

        Property ircMode = config.get("General","mode","server");
        ircMode.comment = "There are two types of connection to the IRC server:\n\t'server': Preferred." +
                          " It connects as an IRC server transparently connecting all players ingame.\n\t" +
                          "'serverbot': Connects as a client with bot repeating everything people are saying";
        irc_mode = ircMode.getString();

        /*
         *  Quirks category
         */
        config.getCategory("Quirks").setComment("Quirks for specific IRC daemons\nIf all settings are set to false we fall back" +
                "to RFC 1459\n\nThis is ignored for 'serverbot' mode");
        Property ircQuirkInspircd = config.get("Quirks","inspircd",false);
        ircQuirkInspircd.comment = "Enable quirks for inspircd.";
        irc_inspircd=ircQuirkInspircd.getBoolean(false);



        Property ircHost = config.get("Irc Connection","host","");
        ircHost.comment = "Server host name. It is usually an url";
        irc_host=ircHost.getString();

        Property ircPort = config.get("Irc Connection","port",6667);
        ircPort.comment = "Connection port. SSL is NOT supported yet.";
        irc_port = ircPort.getInt();

        Property ircAuth = config.get("Irc Connection","auth","");
        ircAuth.comment = "Password for authenticating on server. Leave it empty in 'serverbot' mode";
        irc_auth = ircAuth.getString();

        Property ircRevAuth = config.get("Irc Connection","revAuth","");
        ircRevAuth.comment = "Password for authenticating the other server. Leave it empty in 'serverbot' mode";
        irc_revauth = ircRevAuth.getString();

        Property ircName = config.get("Irc Connection","serverName","localhost");
        ircName.comment = "Server name to pass to the hub server. It's ignored in serverbot mode.";
        irc_servName = ircName.getString();

        Property ircBotName = config.get("Irc Connection","botName","MrSlave");
        ircBotName.comment = "Name of the bot that will appear in the chat.";
        irc_botName=ircBotName.getString();

        Property ircChan = config.get("Irc Connection","ircChannel","minecraft");
        ircChan.comment = "Name of the channel without the starting #.";
        irc_channel=ircChan.getString();

        if(config.hasChanged()){
            config.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent evt){

    }

    @Mod.ServerStarting
    public void serverStarting(FMLServerStartingEvent event){
        if(irc_mode.equals("server")){
            ircService=new IrcServerService();
        }else{
            ircService=new IrcClientService();
        }

        logger.info("Connecting to server.");
        ircService.connect();
        ChatEvents events=new ChatEvents();
        GameRegistry.registerPlayerTracker(events);
        MinecraftForge.EVENT_BUS.register(events);
    }

    @Mod.ServerStopping
    public void serverStopping(FMLServerStoppingEvent evt){
        ircService.disconnect();
    }
}
