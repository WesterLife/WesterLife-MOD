package fr.gabidut76.westerlife.westercore.proxies;

import fr.gabidut76.westerlife.server.Serveur;
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
