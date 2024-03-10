package fr.gabidut76.westerlife.common.network.sync;

import fr.gabidut76.westerlife.westercore.Main;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketSyncClothes implements IMessage{


    String uuid;
    String clothes;
    String action;
    byte activeID;
    int slot;

    public PacketSyncClothes(){}

    public PacketSyncClothes(String uuid, String clothes, String action, byte activeID, int slot) {
        this.uuid = uuid;
        this.clothes = clothes;
        this.action = action;
        this.activeID = activeID;
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = ByteBufUtils.readUTF8String(buf);
        this.clothes = ByteBufUtils.readUTF8String(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
        this.activeID = buf.readByte();
        this.slot = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.uuid);
        ByteBufUtils.writeUTF8String(buf, this.clothes);
        ByteBufUtils.writeUTF8String(buf, this.action);
        buf.writeByte(this.activeID);
        ByteBufUtils.writeVarInt(buf, this.slot, 5);
    }

    public static class Handler implements IMessageHandler<PacketSyncClothes, IMessage> {
        @Override
//        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketSyncClothes m, MessageContext ctx) {
            List<String> clothes2;

            int byteToInt = m.activeID & 0xFF;
            switch (m.action) {
                case "add":

                    break;
                case "remove":

                case "removeall":
                    break;
            }

            return null;
        }
    }
}