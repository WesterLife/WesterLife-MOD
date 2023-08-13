package fr.yan36.westerlife.common.items;

import fr.dynamx.common.contentpack.type.objects.AbstractItemObject;
import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemDrink extends DynamXItem {
    int amount;
    public ItemDrink(String name, int amount, float saturation, ResourceLocation model) {
        super(Main.MODID, name, model);
        this.amount = amount;
        setCreativeTab(Main.WESTER_FOOD);
        setMaxDamage(1);
        setTranslationKey(name);
        setMaxStackSize(2);
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_FOOD;
    }


//    @Override
//    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
//        Databases.getPlayerData((EntityPlayer) entityLiving).setFloat("watervalue", Databases.getPlayerData((EntityPlayer) entityLiving).getFloat("watervalue") + this.amount);
//        return new ItemStack(Items.AIR);
//    }




    @Override
    public AbstractItemObject getInfo() {
        return super.getInfo();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.SUCCESS;
    }


}
