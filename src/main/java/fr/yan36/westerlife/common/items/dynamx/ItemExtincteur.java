package fr.yan36.westerlife.common.items.dynamx;

import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.init.DynamXInit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.HashMap;

public class ItemExtincteur extends DynamXItem {


    public ItemExtincteur(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
        setMaxDamage(100);
        setMaxStackSize(1);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(DynamXInit.Extincteur))) {
                RayTraceResult r = Util.rayTracePlayer(player, 5);
                HashMap<BlockPos, Block> blocks = Util.getBlocksAround(r.getBlockPos(), worldIn, 1);

                blocks.forEach((pos, b) -> {
                    if (b == Blocks.FIRE) {
                        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                    }
                });
            }
        }
        return super.onItemUse(player, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
    }
}
