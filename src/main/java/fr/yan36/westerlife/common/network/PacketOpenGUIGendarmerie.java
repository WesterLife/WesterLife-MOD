package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.client.gui.CSSGuiGendarmerie;
import fr.yan36.westerlife.server.Plainte;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
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
            List<Plainte> plainteList = new ArrayList<>();
            plainteList.add(new Plainte(1, "test", "test", "test"));

            System.out.println("packet recu");
            System.out.println(m.plainteList);

            return null;
        }
    }
}
