package fr.yan36.westerlife.client.gui.ultralight;

import com.labymedia.ultralight.config.UltralightViewConfig;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.ClientProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class WebGuiManager {
    private static final Map<String, WebController> viewControllers = new HashMap<>();

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        for (WebController viewController : viewControllers.values()) {
            if (viewController != null) {
                viewController.update();
            }
        }

        if (Keyboard.isKeyDown(175) && Keyboard.isKeyDown(169)) {
            viewControllers.clear();
        }
    }

    public static WebController registerAndGetViewController(String key, String url) {
        if (viewControllers.containsKey(key)) {
            return viewControllers.get(key);
        }

        UltralightViewConfig config = new UltralightViewConfig();
        config.enableImages(true);
        config.enableJavascript(true);

        WebController controller = new WebController(ClientProxy.renderer,
                ClientProxy.renderer.createView(1920, 1080, config));

        controller.loadURL(url);
        viewControllers.put(key, controller);
        return controller;
    }
}
