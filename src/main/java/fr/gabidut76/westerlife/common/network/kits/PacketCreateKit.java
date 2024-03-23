package fr.gabidut76.westerlife.common.network.kits;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.network.PacketSendNotif;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.common.objects.kits.Kit;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westercore.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.permission.PermissionAPI;

public class PacketCreateKit extends SerializablePacket implements IDnxPacket {


    public PacketCreateKit() {

    }

    public PacketCreateKit(Kit kit) {
        super(kit);
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
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isServer()) {
            if(!context.isCreative() && PermissionAPI.hasPermission(context, "kit.create")) return;

            Kit kit = (Kit) getObjectsIn()[0];
            NemesisLink.NEMESIS_API.addKit(kit);
        }
    }


    public static class ServerHandler implements IMessageHandler<PacketCreateKit, IMessage> {
        @Override
        public IMessage onMessage(PacketCreateKit message, MessageContext ctx) {
            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {

                message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER);
            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketCreateKit, IMessage> {
        @Override
        public IMessage onMessage(PacketCreateKit message, MessageContext ctx) {
            return null;
        }
    }
}