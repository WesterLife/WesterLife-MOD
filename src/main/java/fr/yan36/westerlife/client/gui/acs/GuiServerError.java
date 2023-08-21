package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiServerError extends GuiFrame {

    final GuiScreen previousGuiScreen;

    public GuiServerError(GuiScreen previousGuiScreen, String reason) {
        super(new GuiScaler.Identity());
        this.previousGuiScreen = previousGuiScreen;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("WesterLife");
        title.setCssClass("title");

        GuiLabel action = new GuiLabel("Impossible de se connecter au serveur : " + reason + " ");
        action.setCssClass("action2");
        action.getStyle().setFontSize(1);


        GuiButton cancel = new GuiButton("Retour");
        cancel.setCssClass("cancel");
        cancel.addClickListener((b, m,c) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
        });

        background.add(cancel);


        GuiLabel mention = new GuiLabel("WesterLife n'est pas affilié à Mojang AB.");
        mention.setCssClass("mention");


        background.add(title);
        background.add(action);
        background.add(mention);

        add(background);
    }
    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}