package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.style.ComponentStyleManager;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CSSGuiIngameMenu extends GuiFrame {
    public CSSGuiIngameMenu() {
        super(new GuiScaler.AdjustToScreenSize(true, 1, 1));
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return null;
    }

    @Override
    public Priority getPriority(ComponentStyleManager forT) {
        return super.getPriority(forT);
    }
}
