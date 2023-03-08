package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.acs.CSSGuiChangeSign;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIEditSign implements IMessage{

    private String pos;

    public PacketOpenGUIEditSign() {
    }

    public PacketOpenGUIEditSign(String pos) {
        this.pos = pos;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        pos = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, pos);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIEditSign, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIEditSign m, MessageContext ctx) {
                ACsGuiApi.asyncLoadThenShowGui("editsign", () -> new CSSGuiChangeSign(m.pos));
            return null;
        }
    }
}
