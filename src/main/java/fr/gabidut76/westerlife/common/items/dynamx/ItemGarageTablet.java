package fr.gabidut76.westerlife.common.items.dynamx;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
