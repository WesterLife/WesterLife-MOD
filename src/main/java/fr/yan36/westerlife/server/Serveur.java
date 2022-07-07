package fr.yan36.westerlife.server;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.BlockDistributeur;
import fr.yan36.westerlife.common.network.PacketCreateIdentity;
import fr.yan36.westerlife.common.network.PacketOpenGUIAtm;
import fr.yan36.westerlife.common.network.PacketSyncPlayer;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


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
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.player),MethodesBDD.getPrenom(e.player),MethodesBDD.getSex(e.player),MethodesBDD.getDate(e.player),MethodesBDD.getArgent(e.player),MethodesBDD.getRIB(e.player)), (EntityPlayerMP) e.player);
                //send packet with data of player
            }
            //Main.network.sendTo(new PacketOpenFranceTravail(MethodesBDD.getJobs()), (EntityPlayerMP) e.player);
        }

    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e){
        BlockPos blockPos = e.getPos();
        Block block = e.getWorld().getBlockState(blockPos).getBlock();
        if(block instanceof BlockDistributeur){
            Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.getEntityPlayer()),MethodesBDD.getPrenom(e.getEntityPlayer()),MethodesBDD.getSex(e.getEntityPlayer()),MethodesBDD.getDate(e.getEntityPlayer()),MethodesBDD.getArgent(e.getEntityPlayer()),MethodesBDD.getRIB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
            Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
        }
    }


}
