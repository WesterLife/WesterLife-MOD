package fr.yan36.westerlife.CoreMod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WesterLifeSecurityManager implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if (name.equals("net.minecraftforge.fml.client.SplashProgress")) {

            WesterLifeCoreMod.log.warn("Transforming Minecraft class.");

            String launchDir = System.getProperty("user.dir");
            WesterLifeCoreMod.log.warn("Launch dir: " + launchDir);
            WesterLifeCoreMod.log.info(!launchDir.split("\\\\")[launchDir.split("\\\\").length - 1].replaceAll("\\.", "").equals("WesterLife"));

            if(!launchDir.split("\\\\")[launchDir.split("\\\\").length - 1].replaceAll("\\.", "").equals("WesterLife")) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -2", new Throwable("WesterLifeLibLoader"));
            }

            // make http GET request to localhost:3000

            File file = new File(launchDir + "\\launcher_profiles.json");
            if(!file.exists()) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -3", new Throwable("WesterLifeLibLoader"));
            }
            // read file content
            String content = null;
            try {
                content = FileUtils.readFileToString(file, "utf-8");
            } catch (Exception e) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -4", new Throwable("WesterLifeLibLoader"));
            }

            // parse JSON

            JsonObject jsonObject = new Gson().fromJson(content, JsonObject.class);
            JsonObject account = jsonObject.getAsJsonObject("account");
            String accessToken = account.get("username").getAsString();

            // make request to westerlife.fr/oauth2-westerlife/data?token=*token*

            String url = "https://westerlife.fr/oauth2-westerlife/data?token=" + accessToken;
            String response = null;
            try {
                response = httpGet(url);

                JsonObject responseJson = new Gson().fromJson(response, JsonObject.class);
                String username = responseJson.get("name").getAsString();

                // check with game data
                if(Minecraft.getMinecraft().getSession().getUsername().equals(username)) {
                    WesterLifeCoreMod.log.info("User is authenticated.");
                } else {
                    throw new RuntimeException("WesterLifeLibLoader, ERROR -6", new Throwable("WesterLifeLibLoader"));
                }

                
            } catch (Exception e) {
                throw new RuntimeException("WesterLifeLibLoader, ERROR -5", new Throwable("WesterLifeLibLoader"));
            }




        }
        return basicClass;
    }

    public static String httpGet(String targeturl) throws Exception {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targeturl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

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
