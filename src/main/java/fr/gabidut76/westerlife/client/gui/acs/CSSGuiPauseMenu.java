package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
//import fr.nathanael2611.modularvoicechat.client.gui.GuiConfig;
//import fr.nathanael2611.modularvoicechat.client.voice.audio.MicroManager;
//import fr.nathanael2611.modularvoicechat.client.voice.audio.SpeakerManager;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSGuiPauseMenu extends GuiFrame {
    public CSSGuiPauseMenu() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("WesterLife");
        title.setCssClass("title");
        background.add(title);

        GuiPanel returnInGame = new GuiPanel();
        returnInGame.setCssClass("returnInGame");
        returnInGame.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });
        background.add(returnInGame);

        GuiPanel options = new GuiPanel();
        options.setCssClass("options");
        options.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(null, Minecraft.getMinecraft().gameSettings));
        });
        background.add(options);

        GuiPanel disconnect = new GuiPanel();
        disconnect.setCssClass("disconnect");
        disconnect.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(null));
        });
        background.add(disconnect);

        GuiPanel micSettings = new GuiPanel();
        micSettings.setCssClass("micSettings");
        micSettings.addClickListener((mouseX, mouseY, mouseButton) -> {
//            if (MicroManager.isRunning() && SpeakerManager.isRunning()) {
//                Minecraft.getMinecraft().displayGuiScreen(new GuiConfig());
//            } else {
//                Minecraft.getMinecraft().displayGuiScreen(new GuiErrorScreen("Erreur", "Erreur interne : " + MicroManager.isRunning() + "-" + SpeakerManager.isRunning() +  ". Cette erreur ne devrait survenir. Contactez le staff."));
//            }
        });
        background.add(micSettings);

        GuiLabel notice = new GuiLabel("WesterLife n’est pas affilié à Mojang AB.");
        notice.setCssClass("notice");
        background.add(notice);

        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");
        add(perf);

        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/pausemenu.css"));
    }
}
