package fr.yan36.westerlife.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;

import java.io.File;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        Display.setTitle("WesterLife");

    }

    @Override
    public void init()
    {
        super.init();

    }

    @Override
    public void postInit() {
        super.postInit();
    }



}
