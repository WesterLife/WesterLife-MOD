package fr.yan36.westerlife.common.items;

import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemDrink extends ItemFood implements IHasModel {
    int amount;
    public ItemDrink(String name, int amount, float saturation) {
        super(amount, saturation, true);
        this.amount = amount;
        setRegistryName(name);
        setCreativeTab(Main.WESTER_MAIN);
        ItemInit.ITEMS.add(this);
        setMaxDamage(1);
        setTranslationKey(name);
        setMaxStackSize(2);
    }

    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_MAIN;
    }


    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        Databases.getPlayerData((EntityPlayer) entityLiving).setFloat("watervalue", Databases.getPlayerData((EntityPlayer) entityLiving).getFloat("watervalue") + this.amount);
        return new ItemStack(Items.AIR);
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

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
