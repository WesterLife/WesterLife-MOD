package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSKeypad;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIKeypad implements IMessage{

    private String code;

    public PacketOpenGUIKeypad() {
    }

    public PacketOpenGUIKeypad(String code) {
        this.code = code;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.code = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.code);

    }

    public static class Handler implements IMessageHandler<PacketOpenGUIKeypad, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIKeypad m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("keypayd", () -> new CSSKeypad(m.code));
            return null;
        }
    }
}
