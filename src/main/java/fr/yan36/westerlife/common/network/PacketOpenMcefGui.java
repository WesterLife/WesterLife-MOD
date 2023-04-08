package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Client;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenMcefGui implements IMessage{


    String screenName;

    public PacketOpenMcefGui(){}

    public PacketOpenMcefGui(String screenName) {

        this.screenName = screenName;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.screenName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.screenName);
    }

    public static class Handler implements IMessageHandler<PacketOpenMcefGui, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenMcefGui m, MessageContext ctx) {
            Client.setScreenMcef(m.screenName);
            return null;
        }
    }
}