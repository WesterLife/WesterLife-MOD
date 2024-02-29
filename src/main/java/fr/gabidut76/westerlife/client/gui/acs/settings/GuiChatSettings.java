package fr.gabidut76.westerlife.client.gui.acs.settings;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

@StyleToLoad
public class GuiChatSettings extends GuiFrame {


    public GuiChatSettings() {
        super(new GuiScaler.Identity());

    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/settings/chat.css"));
    }
}