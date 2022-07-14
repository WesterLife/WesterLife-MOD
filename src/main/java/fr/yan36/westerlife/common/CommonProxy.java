package fr.yan36.westerlife.common;

import fr.yan36.westerlife.common.blocks.tileentity.TEBisign;
import fr.yan36.westerlife.common.blocks.tileentity.TESign;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;

public class CommonProxy {
    public void preInit() throws IOException {
        System.out.println("pre init côté commun");
        GameRegistry.registerTileEntity(TESign.class, new ResourceLocation("westerlife", "tesign"));
        GameRegistry.registerTileEntity(TEBisign.class, new ResourceLocation("westerlife", "tebisign"));

    }

    public void init()
    {

    }
}
