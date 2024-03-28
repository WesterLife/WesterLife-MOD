package fr.gabidut76.westerlife.common.capabilities.packets;

import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.utils.Animation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
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
        this.c = new Character();
        NBTTagCompound nbt = ByteBufUtils.readTag(buf);
        if(nbt == null) {
            System.out.println("NBT is null");
            return;
        }
        this.c.deserializeNBT(nbt);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.player,5);
        if(this.animation == null) {
            System.out.println("Animation is null");
            this.animation = Animation.NONE;
        }
        ByteBufUtils.writeVarInt(buf, this.animation.getId(),5);
        if(this.c == null) {
            System.out.println("Character is null");
            this.c = new Character();
        }
        ByteBufUtils.writeTag(buf, this.c.serializeNBT());
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
//                System.out.println("Applying capabilities to player : " + entity);
//                System.out.println("Animation : " + message.animation);
                if(entity != null && entity instanceof EntityPlayerMP) {


                    assert PlayerStatData.PlayerStatProvider.CAPABILITY != null;

                    if(entity.hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)) {
                        Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).setAnimation(message.animation);
                        Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).setCharacter(message.c);
                    }
                }
            });
            return null;
        }
    }
}
