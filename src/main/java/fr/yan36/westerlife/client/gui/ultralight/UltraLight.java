package fr.yan36.westerlife.client.gui.ultralight;

import com.labymedia.ultralight.UltralightLoadException;
import com.labymedia.ultralight.UltralightPlatform;
import com.labymedia.ultralight.UltralightRenderer;
import com.labymedia.ultralight.config.FontHinting;
import com.labymedia.ultralight.config.UltralightConfig;
import fr.yan36.westerlife.client.gui.ultralight.support.ResourceManager;
import net.minecraft.client.Minecraft;

import java.io.File;

public class UltraLight {
    private static UltralightRenderer renderer;

    public static final File ultraLightDir = new File(Minecraft.getMinecraft().gameDir, "natives");
    private UltraLight() {}
    public static void init() {

        try {
            ResourceManager.init();
        } catch (UltralightLoadException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        UltralightPlatform platform = UltralightPlatform.instance();
        platform.setConfig(
                new UltralightConfig()
                        .animationTimerDelay(1.0 / 60)
                        .scrollTimerDelay(1.0 / 60)
                        .fontHinting(FontHinting.SMOOTH)
                        .forceRepaint(false)

        );
        platform.usePlatformFontLoader();
        platform.usePlatformFileSystem(ultraLightDir.getAbsolutePath());
        platform.setLogger((level, message) -> {
            switch (level) {
                case INFO:
                    System.out.println("[INFO] " + message);
                    break;
                case WARNING:
                    System.out.println("[WARNING] " + message);
                    break;
                case ERROR:
                    System.err.println("[ERROR] " + message);
                    break;
            }
        });
        renderer = UltralightRenderer.create();
    }

    public static UltralightRenderer getRenderer() {
        return renderer;
    }
}
