package fr.gabidut76.westerlife.common.network.old;

import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.common.utils.list.Warp;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSendWarpsToPlayer implements IMessage{

    public int uid;
    public String warps;

    public PacketSendWarpsToPlayer(){}

    public PacketSendWarpsToPlayer(int uid, String warps) {
        this.uid = uid;
        this.warps = warps;

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.uid = ByteBufUtils.readVarInt(buf, 5);
        this.warps = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.uid, 5);
        ByteBufUtils.writeUTF8String(buf, this.warps);

    }

    public static class Handler implements IMessageHandler<PacketSendWarpsToPlayer, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSendWarpsToPlayer m, MessageContext ctx) {
            for (Warp warp : Client.warplist) {
                Client.warplist.add(Warp.fromString(m.warps));
            }
            return null;
        }
    }
}