package fr.gabidut76.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.westercore.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketReqOpenInv extends SerializablePacket implements IDnxPacket {



    public PacketReqOpenInv() {
        super(new Object[0]);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    @SideOnly(Side.SERVER)
    public void handleUDPReceive(EntityPlayer context, Side side) {

    }

    public static class ServerHandler implements IMessageHandler<PacketReqOpenInv, IMessage> {
        @Override
        public IMessage onMessage(PacketReqOpenInv message, MessageContext ctx) {
            Objects.requireNonNull(ctx.getServerHandler().player.getServer()).addScheduledTask(() -> {
//                if(ctx.getServerHandler().player.isCreative()) {
//                    Main.network.sendTo(new PacketOpenAcsGui(13,"",""), ctx.getServerHandler().player);
//                    return;
//                }
                ctx.getServerHandler().player.openGui(Main.instance, 7, ctx.getServerHandler().player.world, (int) ctx.getServerHandler().player.posX, (int) ctx.getServerHandler().player.posY, (int) ctx.getServerHandler().player.posZ);
            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketReqOpenInv, IMessage> {
        @Override
        public IMessage onMessage(PacketReqOpenInv message, MessageContext ctx) {

            return null;
        }
    }
}
