package fr.yan36.westerlife.common.items;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.items.tools.ItemRagdoll;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.DynamX.punchingball.TestEntity2;
import fr.yan36.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import fr.yan36.westerlife.server.DiscordWebhook;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.DatabaseManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemPlaceCirculationSign extends Item implements IHasModel {



    public ItemPlaceCirculationSign(String name)
    {
        setRegistryName(name);
        ItemInit.ITEMS.add(this);
        setTranslationKey(name);
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
