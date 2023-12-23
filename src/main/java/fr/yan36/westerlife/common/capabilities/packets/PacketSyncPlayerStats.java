package fr.yan36.westerlife.common.capabilities.packets;

import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatCapability;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.utils.Animation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class PacketSyncPlayerStats implements IMessage {

    int player;
    Animation animation;
    Character c;

    public PacketSyncPlayerStats() {
    }

    public PacketSyncPlayerStats(int player, Animation animation, Character c) {
        this.player = player;
        this.animation = animation;
        this.c = c;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = ByteBufUtils.readVarInt(buf,5);
        this.animation = Animation.getAnimationById(ByteBufUtils.readVarInt(buf,5));
        this.c = Character.fromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.player,5);
        ByteBufUtils.writeVarInt(buf, this.animation.getId(),5);
        ByteBufUtils.writeUTF8String(buf, this.c.toString());
    }

    public static class ServerHandler implements IMessageHandler<PacketSyncPlayerStats, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncPlayerStats message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSyncPlayerStats, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncPlayerStats message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.player);
                if(entity != null && entity instanceof EntityPlayer) {


                    assert PlayerStatCapability.CAPABILITY != null;
                    System.out.println(message.c);
                    Objects.requireNonNull(entity.getCapability(PlayerStatCapability.CAPABILITY, null)).setAnimation(message.animation);
                    Objects.requireNonNull(entity.getCapability(PlayerStatCapability.CAPABILITY, null)).setCharacter(message.c);
                }
            });
            return null;
        }
    }
}
