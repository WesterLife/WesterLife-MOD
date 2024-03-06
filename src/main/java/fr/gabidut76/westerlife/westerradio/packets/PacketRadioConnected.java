package fr.gabidut76.westerlife.westerradio.packets;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.network.PacketNotif;
import fr.gabidut76.westerlife.common.network.PacketSendNotif;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.westercore.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRadioConnected extends SerializablePacket implements IDnxPacket {


    public PacketRadioConnected() {

    }

    public PacketRadioConnected(EntityPlayer p, Notification notification) {
        super(notification, p.getEntityId());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        System.out.println("fromBytes: " + this.getObjectsIn()[1]);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        System.out.println("toBytes: " + this.getObjectsIn()[1]);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {

        }
    }


    public static class ServerHandler implements IMessageHandler<PacketRadioConnected, IMessage> {
        @Override
        public IMessage onMessage(PacketRadioConnected message, MessageContext ctx) {
//            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
//
//                message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER);
//            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketRadioConnected, IMessage> {
        @Override
        public IMessage onMessage(PacketRadioConnected message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
