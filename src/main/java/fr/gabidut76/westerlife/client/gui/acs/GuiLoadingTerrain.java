package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class GuiLoadingTerrain extends GuiFrame {



    public GuiLoadingTerrain(Boolean isServer) {
        super(new GuiScaler.Identity());
        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
//        background.getStyle().setBackgroundColor(0x000000);

        GuiLabel label = new GuiLabel("Chargement...");
        label.setCssClass("action");
//        label.getStyle().setBackgroundColor(0x000000);

        background.add(label);

        add(background);
    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}