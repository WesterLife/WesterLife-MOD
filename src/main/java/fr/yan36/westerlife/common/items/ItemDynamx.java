package fr.yan36.westerlife.common.items;

import fr.dynamx.api.entities.IModuleContainer;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.client.gui.VehicleHud;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.EngineModule;
import fr.dynamx.common.items.DynamXItem;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class ItemDynamx extends DynamXItem {


    public ItemDynamx(String modid, String itemName, String model) {
        super(modid, itemName, model);
        setCreativeTab(Main.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if (worldIn.isRemote) {
            System.out.println("clicked");
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(Main.PistoletRadar))) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 30);
                player.playSound(SoundsHandler.BIP, 0.5f, 1f);
                Minecraft mc = Minecraft.getMinecraft();
                RayTraceResult objectMouseOver = mc.objectMouseOver;
                System.out.println(objectMouseOver);
                if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                    Entity Target = objectMouseOver.entityHit;
                    if (Target instanceof BaseVehicleEntity) {
                        BaseVehiclePhysicsHandler<?> physicsHandler = ((BaseVehicleEntity<?>) Target).physicsHandler;
                        float speed = physicsHandler.getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH);
                        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : " + speed, true);
                        System.out.println(speed);
                    }
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(Main.PistoletRadar))) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 30);
                player.playSound(SoundsHandler.BIP, 0.5f, 1f);
                Minecraft mc = Minecraft.getMinecraft();
                RayTraceResult objectMouseOver = mc.objectMouseOver;
                System.out.println(objectMouseOver);
                if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                    Entity Target = objectMouseOver.entityHit;
                    if (Target instanceof BaseVehicleEntity) {
                        BaseVehiclePhysicsHandler<?> physicsHandler = ((BaseVehicleEntity<?>) Target).physicsHandler;
                        float speed = physicsHandler.getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH);
                        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : " + speed, true);
                    }
                }
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
