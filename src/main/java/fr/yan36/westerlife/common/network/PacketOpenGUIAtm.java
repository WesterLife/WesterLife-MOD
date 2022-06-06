package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.client.gui.CSSGuiChangeSign;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIAtm implements IMessage{

    public PacketOpenGUIAtm() {
    }


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketOpenGUIAtm, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIAtm m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("atm", CSSGuiAtm::new);
            Main.network.sendToServer(new PacketSyncPlayer());
            return null;
        }
    }
}
