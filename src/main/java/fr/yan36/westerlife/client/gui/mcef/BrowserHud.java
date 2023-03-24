package fr.yan36.westerlife.client.gui.mcef;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.montoyo.mcef.api.API;
import net.montoyo.mcef.api.IBrowser;
import net.montoyo.mcef.api.MCEFApi;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class BrowserHud
{
    private static final List<BrowserHud> huds = new ArrayList<BrowserHud>();;
    private String url;
    private API api;
    private IBrowser browser;
    private Minecraft mc;

    public BrowserHud(String url) {
        this.url = url;
        this.api = MCEFApi.getAPI();
        this.browser = this.api.createBrowser(url, true);
        this.mc = Minecraft.getMinecraft();
    }

    public void drawScreen() {
        if (this.browser != null) {
            this.browser.resize(this.mc.displayWidth, this.mc.displayHeight);
            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            this.browser.draw(0.0, (double)scaledResolution.getScaledHeight(), (double)scaledResolution.getScaledWidth(), 0.0);
            GL11.glDisable(3042);
            GlStateManager.enableDepth();
        }
    }

    public static void addHud(final BrowserHud browserHud) {
        BrowserHud.huds.add(browserHud);
    }

    public static void removeHud(final BrowserHud browserHud) {
        BrowserHud.huds.remove(browserHud);
    }

    @SubscribeEvent
    public static void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            for (final BrowserHud browserHud : BrowserHud.huds) {
                browserHud.drawScreen();
            }
        }
    }
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(final InputEvent.KeyInputEvent event) {
        if(Client.keyBindTest.isPressed()){
            Main.browserScreen = new BrowserScreen("mod://westerlife/create_perso/perso1.html");
            Main.browserScreen.openMenu();
        }
    }

    public void runJS(final String js) {
        this.browser.runJS(js, "");
    }
}