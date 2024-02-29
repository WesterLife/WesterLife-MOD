package fr.gabidut76.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.client.gui.acs.CSSGuiEco;
import fr.gabidut76.westerlife.client.renderer.ClientNotifications;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PacketOpenGuiECO extends SerializablePacket implements IDnxPacket {



    public PacketOpenGuiECO() {

    }

    public PacketOpenGuiECO(List<BankAccount> banks, List<Character> chars) {
        super(banks, chars);
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
    @SideOnly(Side.CLIENT)
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isClient()) {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiEco((List<BankAccount>) this.getObjectsIn()[0], (List<Character>) this.getObjectsIn()[1]).getGuiScreen());
        }
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    public static class ServerHandler implements IMessageHandler<PacketOpenGuiECO, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenGuiECO message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketOpenGuiECO, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenGuiECO message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}