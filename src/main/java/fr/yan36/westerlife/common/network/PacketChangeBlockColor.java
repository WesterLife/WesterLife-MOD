package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.acs.CSSGuiColoredBlock;
import fr.yan36.westerlife.client.gui.acs.CSSGuiLights;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.dynamx.BlockColored;
import fr.yan36.westerlife.common.blocks.tileentity.TileColoredBlock;
import fr.yan36.westerlife.common.objects.LightSequence;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketChangeBlockColor implements IMessage{


    BlockPos pos;
    Integer color;

    public PacketChangeBlockColor(){}

    public PacketChangeBlockColor(BlockPos pos, Integer color) {
        this.pos = pos;
        this.color = color;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
        this.color = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, Util.blockPosToString(this.pos));
        ByteBufUtils.writeVarInt(buf, this.color, 5);
    }

    public static class Handler implements IMessageHandler<PacketChangeBlockColor, IMessage> {
        @Override
        public IMessage onMessage(PacketChangeBlockColor m, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;

            if(player.isCreative()) {
                if(player.world.getBlockState(m.pos).getBlock() instanceof BlockColored) {
                    if(player.world.getTileEntity(m.pos) != null) {
                        TileColoredBlock tile = (TileColoredBlock) player.world.getTileEntity(m.pos);
                        assert tile != null;
                        tile.setColor(m.color);
                    }
                }
            }
            return null;
        }
    }
}