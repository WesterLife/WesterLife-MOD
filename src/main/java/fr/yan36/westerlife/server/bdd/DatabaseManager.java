package fr.yan36.westerlife.server.bdd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DatabaseManager {
    private DatabaseAccess westerLifeDB;
    private static Properties props = new Properties();
    private static String host, user, pwd, dbName;
    public DatabaseManager(){
        try {
            props.load(new FileReader(new File("mods/setup/bdd.properties")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        host = props.getProperty("host");
        user = props.getProperty("login");
        pwd = props.getProperty("pwd");
        dbName = props.getProperty("database");
        System.out.println("\n------------------------------------------\n"+
                "Config chargée"+
                "\n------------------------------------------\n");
        DatabaseCredentials credentials = new DatabaseCredentials(host, user, pwd, dbName, 3306);
        westerLifeDB = new DatabaseAccess(credentials);

        westerLifeDB.initPool();
    }

    public DatabaseAccess getWesterLifeDB() {
        return westerLifeDB;
    }

    public void closeAllDatabaseConnections(){
        westerLifeDB.closePool();
    }
}
