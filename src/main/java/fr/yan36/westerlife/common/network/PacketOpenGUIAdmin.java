package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSGuiAdmin;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIAdmin implements IMessage{

    public PacketOpenGUIAdmin() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIAdmin, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIAdmin m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("admin", CSSGuiAdmin::new);
            return null;
        }
    }
}