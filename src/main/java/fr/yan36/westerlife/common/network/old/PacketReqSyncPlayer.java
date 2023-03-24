package fr.yan36.westerlife.common.network.old;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
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
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketReqSyncPlayer m, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(player),MethodesBDD.getPrenom(player),MethodesBDD.getSex(player),MethodesBDD.getDate(player),MethodesBDD.getArgent(player), MethodesBDD.getRIB(player)), player);
            return null;
        }
    }
}