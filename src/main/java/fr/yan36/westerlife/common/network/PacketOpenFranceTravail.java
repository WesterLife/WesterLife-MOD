package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSFranceTravail;
import fr.yan36.westerlife.utils.list.Job;
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

public class PacketOpenFranceTravail implements IMessage{


    private String jobList;

    public PacketOpenFranceTravail() {
    }

    public PacketOpenFranceTravail(List<Job> plainteList) {
        this.jobList = plainteList.toString();
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.jobList = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.jobList);
    }

    public static class Handler implements IMessageHandler<PacketOpenFranceTravail, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenFranceTravail m, MessageContext ctx) {
            List<String> myList = new ArrayList<String>(Arrays.asList(m.jobList.substring(1, m.jobList.length() - 1).split(",")));
            ACsGuiApi.asyncLoadThenShowGui("francetravail", () -> new CSSFranceTravail(myList));

            return null;
        }
    }
}
