package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.client.phone.util.PhoneFrame;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenPhoneFrame implements IMessage{


    public PacketOpenPhoneFrame(){}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class handler implements IMessageHandler<PacketOpenPhoneFrame, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenPhoneFrame m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("phone", PhoneFrame::new);
            return null;
        }
    }
}