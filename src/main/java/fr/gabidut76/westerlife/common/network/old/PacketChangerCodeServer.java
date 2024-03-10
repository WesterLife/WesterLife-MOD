package fr.gabidut76.westerlife.common.network.old;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketChangerCodeServer implements IMessage {

    public int player;
    public String code;

    public PacketChangerCodeServer() {
    }

    public PacketChangerCodeServer(EntityPlayer player, String code) {
        this.player = player.getEntityId();
        this.code = code;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.code = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(player);
        ByteBufUtils.writeUTF8String(buf, code);
    }

    public static class Handler implements IMessageHandler<PacketChangerCodeServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketChangerCodeServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);

            return null;
        }
    }
}
