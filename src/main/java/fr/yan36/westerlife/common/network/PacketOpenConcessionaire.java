package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.yan36.westerlife.client.gui.acs.CSSGuiConcess;
import fr.yan36.westerlife.common.objects.CarDealer;
import fr.yan36.westerlife.common.objects.character.Character;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenConcessionaire extends SerializablePacket implements IDnxPacket {



    public PacketOpenConcessionaire() {
    }

    public PacketOpenConcessionaire(CarDealer character) {
        super(character);
        System.out.println("created packet");
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
    @SideOnly(Side.CLIENT)
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {
            System.out.println("loaded code");
            Minecraft.getMinecraft().addScheduledTask(() -> {

                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiConcess((CarDealer) getObjectsIn()[0]).getGuiScreen());
            });
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketOpenConcessionaire, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenConcessionaire message, MessageContext ctx) {
//            ctx.getServerHandler().player.getServer().addScheduledTask(() -> message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER));
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketOpenConcessionaire, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenConcessionaire message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT));
            return null;
        }
    }
}
