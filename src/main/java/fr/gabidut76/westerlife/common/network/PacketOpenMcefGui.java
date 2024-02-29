package fr.gabidut76.westerlife.common.network;

import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.common.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

import static fr.gabidut76.westerlife.client.Client.needToCreateCharacter;

public class PacketOpenMcefGui implements IMessage {


    String screenName;
    BlockPos pos;

    public PacketOpenMcefGui() {
    }

    public PacketOpenMcefGui(String screenName) {
        this.screenName = screenName;
        this.pos = new BlockPos(-1, -1, -1);
    }

    public PacketOpenMcefGui(String screenName, BlockPos pos) {
        this.screenName = screenName;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.screenName = ByteBufUtils.readUTF8String(buf);
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.screenName);
        ByteBufUtils.writeUTF8String(buf, Objects.requireNonNull(Util.blockPosToString(this.pos)));
    }

    public static class Handler implements IMessageHandler<PacketOpenMcefGui, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenMcefGui m, MessageContext ctx) {
            if (Objects.equals(m.screenName, "perso")) {
                needToCreateCharacter = 1;
            } else {
                Client.setScreenMcef(m.screenName, m.pos);
            }
            return null;
        }
    }
}