package fr.yan36.westerlife.client.phone.apps.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiSettings extends GuiFrame {
    public GuiSettings() {
        super(new GuiScaler.Identity());
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        GuiLabel label = new GuiLabel("Hello !");

        screen.add(label);
        add(screen);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return null;
    }
}
