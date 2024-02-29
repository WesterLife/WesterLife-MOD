package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.client.gui.other.GuiConnecting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CSSGuiMainMenu extends GuiFrame {

    public CSSGuiMainMenu() throws IOException {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel settings = new GuiPanel();
        settings.setCssClass("settings");

        settings.addClickListener((mouseX, mouseY, mouseButton) -> {
            try {
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(new CSSGuiMainMenu().getGuiScreen(), Minecraft.getMinecraft().gameSettings));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        background.add(settings);
        GuiPanel connect = new GuiPanel();
        connect.setCssClass("connect");

        connect.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                Client.isLogginIn = true;
                try {
                    mc.displayGuiScreen(new GuiConnecting(mc));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                try {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiWorldSelection(new CSSGuiMainMenu().getGuiScreen()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GuiPanel exit = new GuiPanel();
        exit.setCssClass("exit");

        exit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().shutdown();
        });

        background.add(exit);

        background.add(connect);

        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");

        background.add(perf);


        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/mainmenu.css"));
    }
}
