package fr.yan36.westerlife.common;

import fr.dynamx.api.events.PhysicsEvent;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.yan36.westerlife.common.blocks.tileentity.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta)
    {

    }

    public void registerVariantRenderer(Item item, int meta, String filename, String id)
    {


    }

    public void registerEntityRenderers()
    {

    }

    public void preInit() throws IOException {
        System.out.println("pre init côté commun");
        GameRegistry.registerTileEntity(TESign.class, new ResourceLocation("westerlife", "tesign"));
        GameRegistry.registerTileEntity(TileTombe.class, new ResourceLocation("westerlife", "tombe"));
        GameRegistry.registerTileEntity(TileMovingGate.class, new ResourceLocation("westerlife", "temovinggate"));
        GameRegistry.registerTileEntity(TEBisign.class, new ResourceLocation("westerlife", "tebisign"));
        GameRegistry.registerTileEntity(TETerminalDePaiement.class, new ResourceLocation("westerlife", "teterminaldepaiement"));
        GameRegistry.registerTileEntity(TileRadarFixe.class, new ResourceLocation("westerlife", "radarfixe"));
        GameRegistry.registerTileEntity(TileHerse.class, new ResourceLocation("westerlife", "herse"));
        GameRegistry.registerTileEntity(TileFeuRouge.class, new ResourceLocation("westerlife", "feurouge"));
        GameRegistry.registerTileEntity(TileSpot.class, new ResourceLocation("westerlife", "spot"));

        MinecraftForge.EVENT_BUS.register(this);


//        GameRegistry.registerTileEntity(TEDigicode.class, new ResourceLocation("westerlife", "tedigicode"));
    }

    public void init()
    {

    }

    public void postInit()
    {

    }
}
