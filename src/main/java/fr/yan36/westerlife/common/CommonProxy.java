package fr.yan36.westerlife.common;

import fr.yan36.westerlife.common.blocks.tileentity.*;
import fr.yan36.westerlife.common.handlers.SoundsHandler;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.common.MinecraftForge;
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
        GameRegistry.registerTileEntity(TileTombe.class, new ResourceLocation("westerlife", "tombe"));
        GameRegistry.registerTileEntity(TileMovingGate.class, new ResourceLocation("westerlife", "temovinggate"));
        GameRegistry.registerTileEntity(TEBisign.class, new ResourceLocation("westerlife", "tebisign"));
        GameRegistry.registerTileEntity(TETerminalDePaiement.class, new ResourceLocation("westerlife", "teterminaldepaiement"));
        GameRegistry.registerTileEntity(TileRadarFixe.class, new ResourceLocation("westerlife", "radarfixe"));
        GameRegistry.registerTileEntity(TileHerse.class, new ResourceLocation("westerlife", "herse"));
        GameRegistry.registerTileEntity(TileFeuRouge.class, new ResourceLocation("westerlife", "feurouge"));
        GameRegistry.registerTileEntity(TileSpot.class, new ResourceLocation("westerlife", "spot"));
        GameRegistry.registerTileEntity(TileLyre.class, new ResourceLocation("westerlife", "lyre"));
        GameRegistry.registerTileEntity(TileScreen.class, new ResourceLocation("westerlife", "screen"));
        GameRegistry.registerTileEntity(TilePoteauLevant.class, new ResourceLocation("westerlife", "tilepoteaulevant"));
        GameRegistry.registerTileEntity(TileChair.class, new ResourceLocation("westerlife", "chair"));
        GameRegistry.registerTileEntity(TileColoredBlock.class, new ResourceLocation("westerlife", "coloredblock"));
        GameRegistry.registerTileEntity(TileIrm.class, new ResourceLocation("westerlife", "irm"));
        GameRegistry.registerTileEntity(TilePorteNom.class, new ResourceLocation("westerlife", "portenom"));
        GameRegistry.registerTileEntity(TilePanneauAgglomeration.class, new ResourceLocation("westerlife", "panneauagglomeration"));
        GameRegistry.registerTileEntity(TilePanneauRue.class, new ResourceLocation("westerlife", "panneaurue"));
        GameRegistry.registerTileEntity(TilePanneauRueSP.class, new ResourceLocation("westerlife", "panneauruesp"));
        GameRegistry.registerTileEntity(TileAIPoint.class, new ResourceLocation("westerlife", "aipoint"));
        GameRegistry.registerTileEntity(TilePark.class, new ResourceLocation("westerlife", "park"));
        GameRegistry.registerTileEntity(TileGarage.class, new ResourceLocation("westerlife", "garage"));
        GameRegistry.registerTileEntity(TileMacdo.class, new ResourceLocation("westerlife", "macdo"));
        SoundsHandler.registerSounds();
        MinecraftForge.EVENT_BUS.register(this);

//        ScreenshotEvent



//        GameRegistry.registerTileEntity(TEDigicode.class, new ResourceLocation("westerlife", "tedigicode"));
    }



    public void init()
    {

    }

    public void postInit()
    {

    }
}
