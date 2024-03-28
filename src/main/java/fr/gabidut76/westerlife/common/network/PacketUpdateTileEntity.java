package fr.gabidut76.westerlife.common.network;

import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.gabidut76.westerlife.common.blocks.tileentity.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class PacketUpdateTileEntity implements IMessage{

    BlockPos pos;
    String forWhat;
    String[] args;

    public PacketUpdateTileEntity(){}

    public PacketUpdateTileEntity(BlockPos pos, String forWhat, String[] args) {
        this.pos = pos;
        this.forWhat = forWhat;
        this.args = args;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
        this.forWhat = ByteBufUtils.readUTF8String(buf);
        this.args = ByteBufUtils.readUTF8String(buf).split(",");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, Util.blockPosToString(this.pos));
        ByteBufUtils.writeUTF8String(buf, this.forWhat);
        ByteBufUtils.writeUTF8String(buf, String.join(",", this.args));
    }


    public static class Handler implements IMessageHandler<PacketUpdateTileEntity, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketUpdateTileEntity m, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            if(!player.canUseCommand(4, "op")) return null;
            if(!player.isCreative()) return null;
            if(Objects.equals(m.forWhat, "prue") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TilePanneauRue te = (TilePanneauRue) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setName(m.args[0]);
                te.setType(BlockPanneauRue.Type.valueOf(m.args[1]));
                te.sync();
                te.syncToClient();
            }
            if(Objects.equals(m.forWhat, "tombe") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileTombe te = (TileTombe) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setText(m.args[0]);
            }
            if(Objects.equals(m.forWhat, "radar") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileRadarFixe te = (TileRadarFixe) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setSpeed(Integer.parseInt(m.args[0]));
                te.sync();
                te.syncToClient();
            }
            if(Objects.equals(m.forWhat, "movinggate") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileMovingGate te = (TileMovingGate) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setOpenForever(Boolean.parseBoolean(m.args[1]));
                System.out.println("setOpenForever: " + Boolean.parseBoolean(m.args[1]));
                te.setPlayer(m.args[0]);
                System.out.println("setPlayer: " + m.args[0]);
                te.sync();
                te.syncToClient();
            }
            if(Objects.equals(m.forWhat, "pagglo") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TilePanneauAgglomeration te = (TilePanneauAgglomeration) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setName(m.args[0]);
                te.sync();
                te.syncToClient();
            }
            if(Objects.equals(m.forWhat, "portenom") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TilePorteNom te = (TilePorteNom) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setName(m.args[0]);
                te.setFunction(m.args[1]);
                te.sync();
                te.syncToClient();
            }
            if(Objects.equals(m.forWhat, "sensor") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {

                TilePlayerSensor te = (TilePlayerSensor) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setRadius(Integer.parseInt(m.args[0]));
                te.setTimepowered(Integer.parseInt(m.args[1]));
                te.setPlayerexcluded(m.args[2]);
            }

            if(Objects.equals(m.forWhat, "carpres") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileCarPresentation te = (TileCarPresentation) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setCar(m.args[0]);
                te.sync();
                te.syncToClient();
            }

            if(Objects.equals(m.forWhat, "feurouge") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileFeuRouge te = (TileFeuRouge) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setPosition(Integer.parseInt(m.args[0]));
                te.setSyncvalue(Integer.parseInt(m.args[1]));
                te.sync();
                te.syncToClient();
            }

            if(Objects.equals(m.forWhat, "spot") && player.isCreative() && Util.isProximity(player, m.pos, 5)) {
                TileSpot te = (TileSpot) player.world.getTileEntity(m.pos);
                assert te != null;
                te.setAngle(Integer.parseInt(m.args[0]));
                te.setColor(Integer.parseInt(m.args[1]));
                te.sync();
                te.syncToClient();
            }

            return null;
        }
    }
}