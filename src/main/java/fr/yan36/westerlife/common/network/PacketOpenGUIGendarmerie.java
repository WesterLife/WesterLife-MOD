package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.utils.list.Plainte;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PacketOpenGUIGendarmerie implements IMessage{


    private String plainteList;

    public PacketOpenGUIGendarmerie() {
    }

    public PacketOpenGUIGendarmerie(List<Plainte> plainteList) {
        this.plainteList = plainteList.toString();
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.plainteList = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.plainteList);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIGendarmerie, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenGUIGendarmerie m, MessageContext ctx) {
             //ACsGuiApi.asyncLoadThenShowGui("gendarmerie", () -> new CSSGuiGendarmerie(myList));

            return null;
        }
    }
}
