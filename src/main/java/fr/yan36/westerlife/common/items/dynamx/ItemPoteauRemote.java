package fr.yan36.westerlife.common.items.dynamx;

import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.tileentity.TilePoteauLevant;
import fr.yan36.westerlife.common.init.DynamXInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPoteauRemote extends DynamXItem {


    public ItemPoteauRemote(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if(!worldIn.isRemote) {
            // get all blocks in 5x5 area
            BlockPos pos = player.getPosition();
            for (int x = -2; x <= 2; x++) {
                for (int y = -2; y <= 2; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                        if (worldIn.getBlockState(blockPos).getBlock() == DynamXInit.poteauLevant) {
                            TilePoteauLevant tilePoteauLevant = (TilePoteauLevant) worldIn.getTileEntity(blockPos);
                            assert tilePoteauLevant != null;
                            tilePoteauLevant.setState("open");
                        }
                    }
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        if (!worldIn.isRemote) {
//            System.out.println("side here2");
//        }
        return super.onItemUse(player, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
    }
}
