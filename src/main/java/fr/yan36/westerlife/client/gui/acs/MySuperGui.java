
package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;


import java.util.List;
import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class MySuperGui extends GuiFrame {

    public MySuperGui() {
        super(new GuiScaler.Identity());
        GuiPanel frame_0 = new GuiPanel();
        frame_0.setCssClass("frame_0");

        add(frame_0);



    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/admin.css"));
    }
}

        