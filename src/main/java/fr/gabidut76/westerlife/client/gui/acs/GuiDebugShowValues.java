package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class GuiDebugShowValues extends GuiFrame {
    public GuiDebugShowValues(List<String> values) {
        super(new GuiScaler.Identity());

        GuiScrollPane background = new GuiScrollPane();
        background.setCssClass("background");

        int i = 0;

        for (String value : values) {
            System.out.println(value);
            GuiLabel label = new GuiLabel(value);
            label.setCssClass("label");
            label.getStyle().setOffsetY(i);
            background.add(label);
            i += 14;
            System.out.println(i);
        }

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/debug.css"));
    }
}
