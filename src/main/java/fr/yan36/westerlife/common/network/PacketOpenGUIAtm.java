package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIAtm implements IMessage{

    public String code;

    public PacketOpenGUIAtm() {

    }

    public PacketOpenGUIAtm(String code) {
        this.code = code;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        code = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, code);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIAtm, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIAtm m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("atm", () -> new CSSGuiAtm(m.code));

            return null;
        }
    }
}
