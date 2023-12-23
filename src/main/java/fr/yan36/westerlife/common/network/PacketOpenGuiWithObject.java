package fr.yan36.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.yan36.westerlife.client.gui.acs.CSSGuiGarage;
import fr.yan36.westerlife.client.gui.acs.macdo.CSSGuiCommand;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.objects.gameplay.MacdoCommand;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class PacketOpenGuiWithObject extends SerializablePacket implements IDnxPacket {



    public PacketOpenGuiWithObject() {
    }

    public PacketOpenGuiWithObject(int guiId, MacdoCommand.Command object, String arg) {
        super(object, guiId, arg);
    }
    public PacketOpenGuiWithObject(int guiId, List<GarageCar> object, String arg) {
        super(object, guiId, arg);
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
            Minecraft.getMinecraft().addScheduledTask(() -> {
                int guiId = (int) getObjectsIn()[1];
                if(guiId == 0) {
                    System.out.println(getObjectsIn()[0]);
                    Minecraft.getMinecraft().displayGuiScreen(new CSSGuiCommand((MacdoCommand.Command) getObjectsIn()[0], (String) getObjectsIn()[2]).getGuiScreen());
                }
                if(guiId == 1) {
                    System.out.println(Arrays.toString(getObjectsIn()));
                    Minecraft.getMinecraft().displayGuiScreen(new CSSGuiGarage(
                            (String) getObjectsIn()[2],
                            (List<GarageCar>) getObjectsIn()[0]
                    ).getGuiScreen());
                }
            });
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketOpenGuiWithObject, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenGuiWithObject message, MessageContext ctx) {
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketOpenGuiWithObject, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenGuiWithObject message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT));
            return null;
        }
    }
}
