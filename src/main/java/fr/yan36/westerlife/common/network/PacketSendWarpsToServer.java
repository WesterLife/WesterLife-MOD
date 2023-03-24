package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import fr.yan36.westerlife.common.utils.list.Warp;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PacketSendWarpsToServer implements IMessage{

    public int uid;

    public PacketSendWarpsToServer(){}

    public PacketSendWarpsToServer(int uid) {
        this.uid = uid;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.uid = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.uid, 5);

    }

    public static class Handler implements IMessageHandler<PacketSendWarpsToServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketSendWarpsToServer m, MessageContext ctx) {
            List<Warp> warps = MethodesBDD.getWarps();
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.uid);
            Main.network.sendTo(new PacketSendWarpsToPlayer(m.uid, warps.toString()), (EntityPlayerMP) e);
            return null;
        }
    }
}