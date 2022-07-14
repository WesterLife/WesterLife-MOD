package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.BufferUtils;

public class BelierMessage implements IMessage{

    String pos;

    public BelierMessage(String pos){
        this.pos = pos;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.pos);
    }

    public static class Handler implements IMessageHandler<BelierMessage, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(BelierMessage m, MessageContext ctx) {
            BlockPos pos = Util.parseBlockPosFromString(m.pos);
            EntityPlayerMP player = ctx.getServerHandler().player;

            // player.world.
            return null;
        }
    }
}