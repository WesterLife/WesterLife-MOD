package fr.yan36.westerlife.common.items.dynamx;

import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.items.DynamXItem;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.init.DynamxInit;
import fr.yan36.westerlife.common.network.old.BelierMessage;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemPaper extends DynamXItem {


    public ItemPaper(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if (!worldIn.isRemote) {
            System.out.println("clicked");
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(DynamxInit.PistoletRadar))) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 30);
                player.playSound(SoundsHandler.BIP, 0.5f, 1f);
                Entity a = Util.getEntityLookAt(player, 80);
                if(a instanceof CarEntity) {
                    CarEntity car = (CarEntity) a;
                    System.out.println(((BaseVehicleEntity<?>) a).getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH));
                    float speed = ((BaseVehicleEntity<?>) a).getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH);
                    if(Math.floor(speed) == -1.00 || Math.floor(speed) == -2.00 ) {
                        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : " + Math.floor(speed) + "KM/H", true);
                    } else {
                        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : 0.00 KM/H", true);
                    }
                } else {
                    Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§4Ce que vous visez n'est pas un véhicule.", true);

                }
            }
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(DynamxInit.Belier))) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 10);
                Minecraft instance = Minecraft.getMinecraft();
                BlockPos pos = instance.objectMouseOver.getBlockPos();
                System.out.println(instance.player.world.getBlockState(pos).getBlock());
                Main.network.sendToServer(new BelierMessage(Util.blockPosToString(pos)));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (player.getHeldItemMainhand().isItemEqual(new ItemStack(DynamxInit.PistoletRadar))) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 30);
                player.playSound(SoundsHandler.BIP, 0.5f, 1f);
                Minecraft mc = Minecraft.getMinecraft();
                RayTraceResult objectMouseOver = mc.objectMouseOver;
                System.out.println(objectMouseOver);
                System.out.println(Util.getEntityLookAt(player, 80));

                }
            }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
