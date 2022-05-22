package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Client;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCreateIdentity implements IMessage{


    public PacketCreateIdentity(){}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class handler implements IMessageHandler<PacketCreateIdentity, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCreateIdentity m, MessageContext ctx) {
            Client.create = 1;
            return null;
        }
    }
}