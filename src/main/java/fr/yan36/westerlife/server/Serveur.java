package fr.yan36.westerlife.server;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketCreateIdentity;
import fr.yan36.westerlife.common.network.PacketOpenFranceTravail;
import fr.yan36.westerlife.common.network.PacketOpenGUIGendarmerie;
import fr.yan36.westerlife.common.network.PacketSyncPlayer;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Objects;


public class Serveur {


    @SubscribeEvent
    public void onConnectToServer(PlayerEvent.PlayerLoggedInEvent e) {
        //System.out.println(MethodesBDD.getPlayerExist(e.player));
        boolean devmod = false;
        if(!devmod) {
            if(!MethodesBDD.getPlayerExist(e.player)){
                System.out.println("Nouveau joueur : "+e.player.getName());
                Main.network.sendTo(new PacketCreateIdentity(), (EntityPlayerMP) e.player);
            }else{
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.player),MethodesBDD.getPrenom(e.player),MethodesBDD.getSex(e.player),MethodesBDD.getDate(e.player),MethodesBDD.getArgent(e.player)), (EntityPlayerMP) e.player);
                //send packet with data of player
            }
            //Main.network.sendTo(new PacketOpenFranceTravail(MethodesBDD.getJobs()), (EntityPlayerMP) e.player);
        }

    }

}
