package fr.yan36.westerlife.common.capabilities.packets;

import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatCapability;
import fr.yan36.westerlife.common.utils.Animation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class PacketSyncAnimation implements IMessage {

    int player;
    Animation animation;

    public PacketSyncAnimation() {
    }

    public PacketSyncAnimation(int player, Animation animation) {
        this.player = player;
        this.animation = animation;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.animation = Animation.getAnimationById(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        buf.writeInt(this.animation.getId());
    }

    public static class ServerHandler implements IMessageHandler<PacketSyncAnimation, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncAnimation message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSyncAnimation, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncAnimation message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.player);
                if(entity != null && entity instanceof EntityPlayer) {
                    assert PlayerStatCapability.CAPABILITY != null;
                    Objects.requireNonNull(entity.getCapability(PlayerStatCapability.CAPABILITY, null)).setAnimation(message.animation);
                }
            });
            return null;
        }
    }
}
