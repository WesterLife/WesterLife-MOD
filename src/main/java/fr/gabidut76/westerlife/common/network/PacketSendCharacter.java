package fr.gabidut76.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.objects.character.Character;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSendCharacter extends SerializablePacket implements IDnxPacket {

    int player;


    public PacketSendCharacter() {
    }

    public PacketSendCharacter(EntityPlayer player, Character character) {
        super(character);
        this.player = player.getEntityId();

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(this.player);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {

        } else {

        }
    }

    public static class ServerHandler implements IMessageHandler<PacketSendCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketSendCharacter message, MessageContext ctx) {
            ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
                message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER);
            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSendCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketSendCharacter message, MessageContext ctx) {
            System.out.println("Recived packet");
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
