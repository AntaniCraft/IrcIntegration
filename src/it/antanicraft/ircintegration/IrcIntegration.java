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
import it.antanicraft.ircintegration.impl.IrcServerBotService;
import it.antanicraft.ircintegration.impl.IrcServerService;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;


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




    /**
     * Loads configs and ensures that we are running on the server
     * @param evt
     */
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent evt){
        logger=evt.getModLog();
        File configFile = evt.getSuggestedConfigurationFile();
        IrcIntegrationConfig config = new IrcIntegrationConfig(configFile);

        if(config.hasChanged()){
            config.save();
        }
    }

    @Mod.Init
    public void init(FMLInitializationEvent evt){

    }

    @Mod.ServerStarting
    public void serverStarting(FMLServerStartingEvent event){
        if(IrcIntegrationConfig.getInstance().getIrcMode().equals("server")){
            ircService=new IrcServerService();
        }else{
            ircService=new IrcServerBotService();
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
