import fr.aym.mps.utils.MpsUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class test {
    public static void main(String[] args) throws IOException {
        URL requestUrl = new URL("https://mps.dynamx.fr/legacy/1.3.0/home.php?access_key=VGZQNFY5UEJ1YmVLTlpwWC1BQ1JFLTQ=");
        byte[] response = MpsUtils.readUrl(requestUrl);
        String responseInString = new String(response, StandardCharsets.UTF_8);


        String[] dt = responseInString.split(";");

        String os = new String(Base64.getDecoder().decode("VGZQNFY5UEJ1YmVLTlpwWC1BQ1JFLTQ=".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        os = os.substring(0, os.indexOf("-"));
        byte[] decodea = Base64.getDecoder().decode(dt[1]);
        decodea = MpsUtils.decrypt(os, decodea);
        int len = decodea.length;
        byte[] sample = new byte[1024];

        for(int i = 0; i < sample.length; ++i) {
            if (i * 10 < len) {
                sample[i] = decodea[i * 10];
            }
        }

        System.out.println(new String(sample, StandardCharsets.UTF_8));
//        config.checkIntegrity(dt[1].length(), dt[1].hashCode(), decodea.length, sample);
    }
}
