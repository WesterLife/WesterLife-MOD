package fr.yan36.westerlife.server;

import fr.yan36.westerlife.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit() throws IOException {
        super.preInit();
        MinecraftForge.EVENT_BUS.register(new Serveur());
    }

    @Override
    public void init()
    {
        super.init();
    }
}
