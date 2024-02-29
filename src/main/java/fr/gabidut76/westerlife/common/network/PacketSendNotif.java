package fr.gabidut76.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.client.renderer.ClientNotifications;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncGarage;
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
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.PermissionAPI;

public class PacketSendNotif extends SerializablePacket implements IDnxPacket {



    public PacketSendNotif() {

    }

    public PacketSendNotif(Notification notification) {
        super(notification);

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
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ClientNotifications.notifications.add((Notification) this.getObjectsIn()[0]);
            });
        }
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    public static class ServerHandler implements IMessageHandler<PacketSendNotif, IMessage> {
        @Override
        public IMessage onMessage(PacketSendNotif message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSendNotif, IMessage> {
        @Override
        public IMessage onMessage(PacketSendNotif message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}