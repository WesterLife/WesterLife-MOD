package fr.yan36.westerlife.common;

import fr.yan36.westerlife.common.blocks.tileentity.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
        GameRegistry.registerTileEntity(TileMovingGate.class, new ResourceLocation("westerlife", "temovinggate"));
        GameRegistry.registerTileEntity(TEBisign.class, new ResourceLocation("westerlife", "tebisign"));
        GameRegistry.registerTileEntity(TETerminalDePaiement.class, new ResourceLocation("westerlife", "teterminaldepaiement"));
        GameRegistry.registerTileEntity(TileRadarFixe.class, new ResourceLocation("westerlife", "radarfixe"));

        GameRegistry.registerTileEntity(TEDigicode.class, new ResourceLocation("westerlife", "tedigicode"));
    }

    public void init()
    {

    }

    public void postInit()
    {

    }
}
