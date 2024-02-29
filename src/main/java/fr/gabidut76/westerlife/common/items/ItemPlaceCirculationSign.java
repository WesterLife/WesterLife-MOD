package fr.gabidut76.westerlife.common.items;

import fr.dynamx.common.entities.PhysicsEntity;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemPlaceCirculationSign extends Item implements IHasModel {



    public ItemPlaceCirculationSign(String name)
    {
        ItemInit.ITEMS.add(this);
        setTranslationKey(name);
        setRegistryName(Main.MODID, name);
    }


    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_CARDS;
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            PhysicsEntity<?> entity = new WarningSignEntity(worldIn, playerIn.rotationYaw);
            RayTraceResult result = playerIn.rayTrace(10, 1);

            BlockPos p = result.getBlockPos();

            entity.setPositionAndRotation(p.getX(), p.getY() + 1, p.getZ(),  playerIn.rotationYaw,0);
            worldIn.spawnEntity(entity);

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
