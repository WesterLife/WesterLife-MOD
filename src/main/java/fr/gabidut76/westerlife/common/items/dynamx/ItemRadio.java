package fr.gabidut76.westerlife.common.items.dynamx;

import fr.dynamx.common.items.DynamXItem;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.westerradio.RadioHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemRadio extends DynamXItem {


    public ItemRadio(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
        setMaxStackSize(1);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{Main.WESTER_MAIN};
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            System.out.println("ok?");
            if(!player.getHeldItem(hand).hasTagCompound()) {
                player.getHeldItem(hand).setTagCompound(new net.minecraft.nbt.NBTTagCompound());
            }
            if(player.getHeldItem(hand).getTagCompound().hasKey("frequency")) {
                RadioHandler.disconnectPlayerFromFrequency(player);
                player.getHeldItem(hand).getTagCompound().removeTag("frequency");
                return EnumActionResult.SUCCESS;
            } else {
                player.getHeldItem(hand).getTagCompound().setString("frequency", "50");
                RadioHandler.connectPlayerToFrequency("50", player);
                return EnumActionResult.SUCCESS;
            }


        }
        return super.onItemUse(player, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
    }
}
