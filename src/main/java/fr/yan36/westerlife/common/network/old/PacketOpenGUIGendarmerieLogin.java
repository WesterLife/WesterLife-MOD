package fr.yan36.westerlife.common.network.old;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.acs.gendarmerie.CSSGuiGendarmerieLogin;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIGendarmerieLogin implements IMessage{


    public PacketOpenGUIGendarmerieLogin() {
    }


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketOpenGUIGendarmerieLogin, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIGendarmerieLogin m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("gendarmerielogin", CSSGuiGendarmerieLogin::new);
            return null;
        }
    }
}
