package fr.yan36.westerlife.serveur;

import fr.yan36.westerlife.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        MinecraftForge.EVENT_BUS.register(new Serveur());

    }

    @Override
    public void init()
    {
        super.init();
    }
}
