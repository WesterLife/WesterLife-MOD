package fr.yan36.westerlife.server;

import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.server.bdd.DatabaseManager;
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
        fr.yan36.westerlife.server.bdd.DBUtils.initDatabaseSchema();
        fr.yan36.westerlife.server.entreprises.CompanyManager.getInstance().init();
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
