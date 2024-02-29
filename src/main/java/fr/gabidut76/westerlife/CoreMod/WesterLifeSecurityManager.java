package fr.gabidut76.westerlife.CoreMod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WesterLifeSecurityManager {

    public static boolean SHOULD_MANUAL_LOGIN = false;
    public static String NEMESIS_URL = "http://nemesis.westerlife.fr/";

    @SideOnly(Side.CLIENT)
    public static void makesecurity() {

            String launchDir = System.getProperty("user.dir");
            WesterLifeCoreMod.log.warn("Launch dir: " + launchDir);
            WesterLifeCoreMod.log.info(!launchDir.split("\\\\")[launchDir.split("\\\\").length - 1].replaceAll("\\.", "").equals("WesterLife"));

            if(!launchDir.split("\\\\")[launchDir.split("\\\\").length - 1].replaceAll("\\.", "").equals("WesterLife")) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -2", new Throwable("WesterLifeLibLoader"));
            }

            // make http GET request to localhost:3000

            File file = new File(launchDir + "\\launcher_profiles.json");
            if(!file.exists()) {
                SHOULD_MANUAL_LOGIN = true;
                return;
            }
            String content = null;
            try {
                content = FileUtils.readFileToString(file, "utf-8");
            } catch (Exception e) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -4", new Throwable("WesterLifeLibLoader"));
            }

            JsonObject jsonObject = new Gson().fromJson(content, JsonObject.class);
            String accessToken = jsonObject.get("account").getAsJsonObject().get("username").getAsString();

            String response = null;
            try {
                response = httpPost(NEMESIS_URL + "api/auth/checktoken", "{\"token\":\"" + accessToken + "\"}");
            } catch (Exception e) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -3", new Throwable("WesterLifeLibLoader"));
            }

            if(response == null) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -1", new Throwable("WesterLifeLibLoader"));
            }

            JsonObject responseJson = new Gson().fromJson(response, JsonObject.class);

            try {
                if(responseJson.get("data").getAsString().equals("")) {
                    SHOULD_MANUAL_LOGIN = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    public static String httpPost(String uri, String body) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            connection.getOutputStream().write(body.getBytes());

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
