package fr.gabidut76.westerlife.common.capabilities.packets;

import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncExtraItem implements IDnxPacket {

    int player;
    int slot;
    ItemStack stack;

    public PacketSyncExtraItem() {
    }

    public PacketSyncExtraItem(EntityPlayer player, int slot, ItemStack stack) {
        this.player = player.getEntityId();
        this.slot = slot;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity p = Minecraft.getMinecraft().player.world.getEntityByID(this.player);
                if (p != null && p instanceof EntityPlayer) {
                    ((IExtraItemHandler) p.getCapability((Capability) ExtraItemCapability.CAPABILITY, (EnumFacing) null)).setStackInSlot(slot, this.stack);
                }
            });
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketSyncExtraItem, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncExtraItem message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSyncExtraItem, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncExtraItem message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
