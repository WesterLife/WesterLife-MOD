package fr.yan36.westerlife.common.items.dynamx;

import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.init.DynamXInit;
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

public class ItemGarageTablet extends ItemDynamx {


    public ItemGarageTablet(String modid, String itemName, ResourceLocation model) {
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

        }
        return super.onItemUse(player, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
    }
}
