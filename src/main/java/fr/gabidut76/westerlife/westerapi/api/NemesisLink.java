package fr.gabidut76.westerlife.westerapi.api;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class NemesisLink {

    public static NemesisAPI NEMESIS_API;

    private static Properties props = new Properties();
    public static String SERVER_API_URL, SERVER_API_KEY;

    public static void init() throws IOException {
        File file = new File("config/westerlife/api.properties");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        props.load(new FileReader(file));
        System.out.println(props);
        SERVER_API_URL = props.getProperty("API_URL");
        SERVER_API_KEY = props.getProperty("API_KEY");

        System.out.println("API_URL: " + SERVER_API_URL);

        NEMESIS_API = new NemesisAPI(SERVER_API_URL, SERVER_API_KEY);
    }
}
