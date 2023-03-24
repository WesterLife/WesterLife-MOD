package fr.yan36.westerlife.common.network.old;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.acs.phone.CSSGuiPhone;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketDisplayPhone implements IMessage {

    public PacketDisplayPhone() {}


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ServerHandler implements IMessageHandler<PacketDisplayPhone, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketDisplayPhone m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("phone", CSSGuiPhone::new);
            return null;
        }
    }
}
