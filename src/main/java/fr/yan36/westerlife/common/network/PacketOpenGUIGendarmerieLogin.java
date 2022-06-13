package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.gendarmerie.CSSGuiGendarmerie;
import fr.yan36.westerlife.client.gui.gendarmerie.CSSGuiGendarmerieLogin;
import fr.yan36.westerlife.server.Plainte;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
