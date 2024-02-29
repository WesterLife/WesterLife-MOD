package fr.gabidut76.westerlife.client.gui.ultralight.support;

import com.labymedia.ultralight.UltralightJava;
import com.labymedia.ultralight.UltralightLoadException;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;
import com.labymedia.ultralight.os.OperatingSystem;
import net.minecraft.client.Minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceManager {
    public static void init() throws UltralightLoadException {
        Path nativesDir = Paths.get(Minecraft.getMinecraft().gameDir.getPath(), "natives");
        String[] libs = new String[] {
                "glib-2.0-0",
                "gobject-2.0-0",
                "gmodule-2.0-0",
                "gio-2.0-0",
                "gstreamer-full-1.0",
                "gthread-2.0-0"
        };
        OperatingSystem os = OperatingSystem.get();
        for(String lib : libs) {
            System.out.println(lib);
            System.load(nativesDir.resolve(os.mapLibraryName(lib)).toAbsolutePath().toString());
        }

        UltralightJava.load(nativesDir);

        UltralightGPUDriverNativeUtil.load(nativesDir);
    }
}
