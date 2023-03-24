package fr.yan36.westerlife.common.network.old;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.mcef.BrowserScreen;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIMcef implements IMessage{

    public String url;

    public PacketOpenGUIMcef() {

    }

    public PacketOpenGUIMcef(String url) {
        this.url = url;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        url = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, url);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIMcef, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIMcef m, MessageContext ctx) {
            Main.browserScreen = new BrowserScreen();
            Main.browserScreen.openMenu();
            return null;
        }
    }
}
