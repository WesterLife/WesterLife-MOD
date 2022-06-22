package fr.yan36.westerlife.client.phone.apps.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiAppManager extends GuiFrame {
    public GuiAppManager() {
        super(new GuiScaler.Identity());
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.add(new GuiLabel("Hello !"));
        add(screen);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return null;
    }
}
