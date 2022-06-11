package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketReqSyncPlayer implements IMessage{



    public PacketReqSyncPlayer(){}


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketReqSyncPlayer, IMessage> {
        @Override
        public IMessage onMessage(PacketReqSyncPlayer m, MessageContext ctx) {
            System.out.println("PacketReqSyncPlayer !"); // DEBUG
            // Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.player),MethodesBDD.getPrenom(e.player),MethodesBDD.getSex(e.player),MethodesBDD.getDate(e.player),MethodesBDD.getArgent(e.player)), (EntityPlayerMP) e.player);
            System.out.println(ctx.side);
            return null;
        }
    }
}