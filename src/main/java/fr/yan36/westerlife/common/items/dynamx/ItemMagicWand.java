package fr.yan36.westerlife.common.items.dynamx;

import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.items.DynamXItem;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauRue;
import fr.yan36.westerlife.common.handlers.SoundsHandler;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.network.old.BelierMessage;
import fr.yan36.westerlife.common.objects.IObjectEditable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemMagicWand extends DynamXItem {


    public ItemMagicWand(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
        setCreativeTab(Main.WESTER_STAFF);
    }

}
