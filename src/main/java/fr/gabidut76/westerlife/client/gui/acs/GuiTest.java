package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.client.gui.ultralight.CSSGuiWebPanel;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class GuiTest extends GuiFrame {


    public GuiTest() {
        super(new GuiScaler.Identity());

        CSSGuiWebPanel panel = new CSSGuiWebPanel("https://www.google.com");
        panel.setCssId("panel");

        add(panel);

    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}