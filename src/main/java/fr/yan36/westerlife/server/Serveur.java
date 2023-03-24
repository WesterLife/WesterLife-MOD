package fr.yan36.westerlife.server;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.dynamx.BlockDistributeur;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.network.PacketAskToCreateCharacter;
import fr.yan36.westerlife.common.network.old.PacketOpenGUIAtm;
import fr.yan36.westerlife.common.network.old.PacketSyncPlayer;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


public class Serveur {
    @Mod.EventHandler
    public void onserverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new WesterLifeCommand());
    }

    @SubscribeEvent
    public void onConnectToServer(PlayerEvent.PlayerLoggedInEvent e) {
        boolean devmod = false;
        if(!devmod) {
            if(!MethodesBDD.getCharacterExists(e.player)){
                System.out.println("Nj debug");
                Main.network.sendTo(new PacketAskToCreateCharacter(), (EntityPlayerMP) e.player);
                e.player.sendMessage(new TextComponentString("Vous n'avez pas de personnage, veuillez en créer un."));

            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e){
        BlockPos blockPos = e.getPos();
        Block block = e.getWorld().getBlockState(blockPos).getBlock();
        if(block instanceof BlockDistributeur) {
            if(e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.CARTE_BANCAIRE))) {
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.getEntityPlayer()), MethodesBDD.getPrenom(e.getEntityPlayer()), MethodesBDD.getSex(e.getEntityPlayer()), MethodesBDD.getDate(e.getEntityPlayer()), MethodesBDD.getArgent(e.getEntityPlayer()), MethodesBDD.getRIB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
                Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
            } else {
                e.getEntityPlayer().sendMessage(new TextComponentString("§cVeuillez insérer votre carte bancaire."));
            }
        }
    }


}
