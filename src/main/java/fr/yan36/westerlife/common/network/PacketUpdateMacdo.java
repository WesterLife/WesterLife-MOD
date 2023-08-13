package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileMacdo;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.*;

public class PacketUpdateMacdo implements IMessage {


    String sel = "";
    String pos = "";

    public PacketUpdateMacdo() {
    }

    public PacketUpdateMacdo(String sel, String pos) {
        this.sel = sel;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.sel = ByteBufUtils.readUTF8String(buf);
        this.pos = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.sel);
        ByteBufUtils.writeUTF8String(buf, this.pos);
    }

    public static class Handler implements IMessageHandler<PacketUpdateMacdo, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateMacdo m, MessageContext ctx) {
            BlockPos pos = Util.parseBlockPosFromString(m.pos);
            if(ctx.getServerHandler().player.world.getBlockState(pos).getBlock().equals(DynamXInit.macdo) && ctx.getServerHandler().player.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) < 5) {
                // if m.sel is not in TileMacdo.burger, send a message to the player

                if(Objects.equals(m.sel, "END")) {
                    TileMacdo tile = (TileMacdo) ctx.getServerHandler().player.world.getTileEntity(pos);
                    List<TileMacdo.burger> ingredients = tile.getBurgeringredients();
                    String burgerComposition = ingredients.stream().map(Enum::name).reduce((s, s2) -> s + ", " + s2).orElse("Empty");
                    ctx.getServerHandler().player.sendMessage(new TextComponentString("Your burger is composed of: " + burgerComposition));

                    ItemStack burger = new ItemStack(DynamXInit.burger);
                    burger.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
                    burger.getTagCompound().setString("burger", burgerComposition);
                    ctx.getServerHandler().player.inventory.addItemStackToInventory(burger);

                    tile.setBurgeringredients(new ArrayList<>());
                    return null;
                }

                if(Arrays.stream(TileMacdo.burger.values()).map(Enum::name).noneMatch(m.sel::equals)) {
                    ctx.getServerHandler().player.sendMessage(new TextComponentString("This ingredient doesn't exist"));
                    return null;
                }
                TileMacdo.burger sel = TileMacdo.burger.valueOf(m.sel);

                TileMacdo tile = (TileMacdo) ctx.getServerHandler().player.world.getTileEntity(pos);
                if(tile != null) {

                    EntityPlayerMP player = ctx.getServerHandler().player;
                    if(!player.inventory.hasItemStack(new ItemStack(sel.getAssociated_item()))) {
                        player.sendMessage(new TextComponentString("You don't have this ingredient"));
                        return null;
                    } else {
                        player.inventory.clearMatchingItems(sel.getAssociated_item(), 0, 1, null);
                    }

                    tile.addBurgeringredient(Collections.singletonList(sel));
                    tile.sync();
                    tile.syncToClient();
                }
            }

            return null;
        }
    }
}