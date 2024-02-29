package fr.gabidut76.westerlife.common.capabilities.packets;

import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.PlayerChunkRelCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncChunk implements IDnxPacket {

    int co2;
    int pollen;
    int x;
    int y;

    public PacketSyncChunk() {
    }

    public PacketSyncChunk(int x, int y, int co2, int pollen) {
        this.co2 = co2;
        this.pollen = pollen;
        this.x = x;
        this.y = y;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.co2 = buf.readInt();
        this.pollen = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.co2);
        buf.writeInt(this.pollen);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if (side.isClient()) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Chunk chunk = Minecraft.getMinecraft().world.getChunk(this.x, this.y);

                chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).setCO2(this.co2);
                chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).setPollen(this.pollen);
            });
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketSyncChunk, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncChunk message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSyncChunk, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncChunk message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
