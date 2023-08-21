package fr.yan36.westerlife.common.capabilities.packets;

import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.objects.GarageCar;
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

import java.util.Arrays;
import java.util.List;

public class PacketSyncGarage extends SerializablePacket implements IDnxPacket {

    int player;


    public PacketSyncGarage() {
    }

    public PacketSyncGarage(EntityPlayer player, List<GarageCar> car) {
        super(car);
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
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity p = Minecraft.getMinecraft().player.world.getEntityByID(this.player);
                System.out.println(p);
                if (p != null && p instanceof EntityPlayer) {
                    if(p.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                        PlayerGarage garage = (PlayerGarage) p.getCapability(PlayerGarageCapability.CAPABILITY, null);
                        assert garage != null;
                        garage.setCars((List<GarageCar>) this.getObjectsIn()[0]);

                    }
                }
            });
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketSyncGarage, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncGarage message, MessageContext ctx) {
            System.out.println("Recived packet");
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketSyncGarage, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncGarage message, MessageContext ctx) {
            System.out.println("Recived packet");
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
