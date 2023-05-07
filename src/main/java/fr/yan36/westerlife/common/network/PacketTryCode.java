package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TEDigicode;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketTryCode implements IMessage{

    String code;
    BlockPos pos;

    public PacketTryCode(){}

    public PacketTryCode(String code, BlockPos pos) {

        this.code = code;
        this.pos = pos;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.code = ByteBufUtils.readUTF8String(buf);
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.code);
        ByteBufUtils.writeUTF8String(buf, Objects.requireNonNull(Util.blockPosToString(this.pos)));
    }

    public static class Handler implements IMessageHandler<PacketTryCode, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketTryCode m, MessageContext ctx) {
            EntityPlayer player = (EntityPlayer) ctx.getServerHandler().player;
            TEDigicode teDigicode = (TEDigicode) player.world.getTileEntity(m.pos);
                if(teDigicode != null) {
                    if(TEDigicode.getCode().equals(m.code)) {
                        player.sendMessage(new TextComponentString("§aCode correct"));
                        //WIP : Accept sound & Emit redstone signal
                    } else {
                        player.sendMessage(new TextComponentString("§cCode incorrect"));
                        //WIP : Deny sound
                    }
                }
            return null;
        }
    }
}