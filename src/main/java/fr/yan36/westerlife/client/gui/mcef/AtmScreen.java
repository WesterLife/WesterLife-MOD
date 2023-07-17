package fr.yan36.westerlife.client.gui.mcef;

import fr.nathanael2611.modularvoicechat.client.gui.GuiConfig;
import fr.nathanael2611.modularvoicechat.client.voice.audio.MicroManager;
import fr.nathanael2611.modularvoicechat.client.voice.audio.SpeakerManager;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.client.gui.acs.CSSCreateCharacter;
import fr.yan36.westerlife.common.network.PacketAnimation;
import fr.yan36.westerlife.common.network.PacketCreateCharacter;
import fr.yan36.westerlife.common.network.PacketTryCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.montoyo.mcef.api.*;
import net.montoyo.mcef.example.ScreenCfg;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AtmScreen extends GuiScreen implements IJSQueryHandler {
    IBrowser browser;
    private API api;
    private String urlToLoad;

    public AtmScreen() {
        this("mod://westerlife/atm/login.html");
    }

    public AtmScreen(String url) {
        this.urlToLoad = (url);
        this.api = MCEFApi.getAPI();
        this.browser = this.api.createBrowser(this.urlToLoad, true);
        Main.logger.debug(urlToLoad);
        this.api.registerJSQueryHandler(this);

    }

    public void initGui() {
        this.browser.resize(this.mc.displayWidth, this.mc.displayHeight);
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
    }

    public int scaleY(final int y) {
        final double sy = y / (double) this.height * this.mc.displayHeight;
        return (int) sy;
    }

    public void loadURL(final String url) {
        if (this.browser == null) {
            this.urlToLoad = url;
        } else {
            this.browser.loadURL(url);
        }
    }

    public void updateScreen() {
        if (this.urlToLoad != null && this.browser != null) {
            this.browser.loadURL(this.urlToLoad);
            this.urlToLoad = null;
        }
    }

    public void drawScreen(final int i1, final int i2, final float f) {
        super.drawScreen(i1, i2, f);
        if (this.browser != null) {
            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.browser.resize(this.mc.displayWidth, this.mc.displayHeight);
            this.browser.draw(0.0, (double) this.height, (double) this.width, 0.0);
            GlStateManager.enableDepth();
        }
    }

    public void onGuiClosed() {
        Main.browserScreen = null;
        Keyboard.enableRepeatEvents(false);
        browser.close();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            System.out.println("execute");
            executeJS("window.angular.setText(\"etsettregt\")");
        }
    }

    @Override
    public void handleInput() {
        while (Keyboard.next()) {
            final boolean pressed = Keyboard.getEventKeyState();
            final char key = Keyboard.getEventCharacter();
            final int modifiers = (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) ? 4 : 0;
            final int num = Keyboard.getEventKey();
            if (this.browser == null) {
                continue;
            }
            if (pressed) {
                this.browser.injectKeyPressedByKeyCode(num, key, modifiers);
            } else {
                this.browser.injectKeyReleasedByKeyCode(num, key, modifiers);
            }
            if (num == 14 && pressed) {
                this.browser.runJS("document.execCommand('delete');", null);
                return;
            }
            if (key == '.') {
                final String dot = ".";
                this.browser.injectKeyTyped(dot.charAt(0), 0);
                return;
            }
            if (modifiers == 4 && num == 47 && !pressed) {
                try {
                    final String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    for (final char c : data.toCharArray()) {
                        this.browser.injectKeyTyped(c, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (key == '\0') {
                    continue;
                }
                this.browser.injectKeyTyped(key, modifiers);
            }
        }
        while (Mouse.next()) {
            final int btn = Mouse.getEventButton();
            final boolean pressed2 = Mouse.getEventButtonState();
            final int sx = Mouse.getEventX();
            final int sy = Mouse.getEventY();
            final int wheel = Mouse.getEventDWheel();
            if (this.browser != null) {
                final int y = this.mc.displayHeight - sy;
                if (wheel != 0) {
                    this.browser.injectMouseWheel(sx, y, 0, 1, wheel);
                } else if (btn == -1) {
                    this.browser.injectMouseMove(sx, y, 0, y < 0);
                } else {
                    this.browser.injectMouseButton(sx, y, 0, btn + 1, pressed2, 1);
                }
            }
            if (pressed2) {
                final int x = sx * this.width / this.mc.displayWidth;
                final int y2 = this.height - sy * this.height / this.mc.displayHeight - 1;
                try {
                    this.mouseClicked(x, y2, btn);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void actionPerformed(final GuiButton src) {
        if (this.browser == null) {
            return;
        }
        if (src.id == 0) {
            this.browser.goBack();
        } else if (src.id == 1) {
            this.browser.goForward();
        } else if (src.id == 3) {
            this.mc.displayGuiScreen(null);
        } else if (src.id == 4) {
            final String loc = this.browser.getURL();
            final String vId = null;
            final boolean redo = false;
            if (vId != null || redo) {
                this.mc.displayGuiScreen(new ScreenCfg(this.browser, vId));
            }
        }
    }

    @Override
    public boolean handleQuery(IBrowser b, long queryId, String query, boolean persistent, IJSQueryCallback cb) {
        return false;
    }

    public void closeActiveGui() {
        //this.executeJS("vue.closeAll();");
        this.mc.displayGuiScreen((GuiScreen) null);
    }

    public void cancelQuery(final IBrowser iBrowser, final long l) {
    }

    public void openBrowserScreen() {
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void openMenu() {
        this.openBrowserScreen();
    }

    public void executeJS(String jsString) {
        this.browser.runJS(jsString, "");
    }

    public String getTextFromUrl() {
        final String string = this.browser.getURL();
        final String[] splitString = string.split("#");
        return splitString[1];
    }
}