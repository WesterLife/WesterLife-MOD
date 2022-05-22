package fr.yan36.westerlife.common.items;

import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDynamx extends DynamXItem {


    public ItemDynamx(String modid, String itemName, String model) {
        super(modid, itemName, model);
        setCreativeTab(Main.creativeTab);
    }

    /** @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(Main.PistoletRadar))){
            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItemMainhand().getItem(), 30);
            playerIn.playSound(SoundsHandler.BIP, 0.5f, 1f);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }**/

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(player.getHeldItemMainhand().isItemEqual(new ItemStack(Main.PistoletRadar))){
            player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 30);
            player.playSound(SoundsHandler.BIP, 0.5f, 1f);
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
