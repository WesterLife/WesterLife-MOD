package fr.gabidut76.westerlife.westercore.proxies;

import fr.gabidut76.westerlife.server.Serveur;
import fr.gabidut76.westerlife.westerapi.bdd.DatabaseManager;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;

public class ServerProxy extends CommonProxy {

    private static DatabaseManager databaseManager;

    @Override
    public void preInit() throws IOException {
        super.preInit();
        MinecraftForge.EVENT_BUS.register(new Serveur());
         databaseManager = new DatabaseManager();
    }

    @Override
    public void init()
    {
        super.init();
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
