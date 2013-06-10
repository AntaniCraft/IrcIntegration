/**
 * Copyright (c) admiral0, 2013
 *
 * IrcIntegration is distributed under the terms of the General Minecraft Mod
 * Public License 1.0, or GMMPL. Please check the contents of the license
 * located in the file LICENSE
 */
package it.antanicraft.ircintegration;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ChatEvents implements IPlayerTracker {

    public static void handleMessage(String remoteUser,String message){
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(String.format("[IRC]%s:%s",remoteUser,message));
    }

    @ForgeSubscribe
    public void someoneDied(LivingDeathEvent evt){
        if(evt.entity instanceof EntityPlayer){
            IrcIntegration.ircService.sendMeMessage("",evt.source.getDeathMessage(evt.entityLiving));
        }
    }

    @ForgeSubscribe
    public void someoneChatted(ServerChatEvent evt){
        IrcIntegration.instance.ircService.sendMessage(evt.username, evt.message);
    }

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        IrcIntegration.instance.ircService.sendUserLogged(player.username,true);
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        IrcIntegration.instance.ircService.sendUserLogged(player.username,false);
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
        //NOP
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {
        //NOP
    }
}
