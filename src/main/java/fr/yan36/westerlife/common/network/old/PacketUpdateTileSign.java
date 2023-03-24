package fr.yan36.westerlife.common.network.old;

import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TESign;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketUpdateTileSign implements IMessage {

    String pos, text, color;

    public PacketUpdateTileSign() {}

    public PacketUpdateTileSign(String pos, String text, String color){

        this.pos = pos;
        this.text = text;
        this.color = color;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = ByteBufUtils.readUTF8String(buf);
        this.text = ByteBufUtils.readUTF8String(buf);
        this.color = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, this.pos);
        ByteBufUtils.writeUTF8String(buf, this.text);
        ByteBufUtils.writeUTF8String(buf, this.color);

    }

    public static class Handler implements IMessageHandler<PacketUpdateTileSign, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketUpdateTileSign m, MessageContext ctx) {
            World w = ctx.getServerHandler().player.world;
            Objects.requireNonNull(w.getTileEntity(Util.parseBlockPosFromString(m.pos))).getTileData().setString("text", m.text);
            TESign tes = (TESign) w.getTileEntity(Util.parseBlockPosFromString(m.pos));
            assert tes != null;
            tes.setText(m.text);

            return null;
        }
    }
}
