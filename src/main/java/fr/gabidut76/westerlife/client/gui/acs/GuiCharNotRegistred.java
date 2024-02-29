package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class GuiCharNotRegistred extends GuiFrame {



    public GuiCharNotRegistred() {
        super(new GuiScaler.Identity());
        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel label = new GuiLabel("Impossible de vous connecter.");
        label.setCssClass("title");

        GuiLabel label2 = new GuiLabel("Votre compte WesterLife ne contient pas de personnage.\nPour régler ce problème, veuillez ouvrir un ticket.");

        label2.setCssClass("action");

        GuiLabel title = new GuiLabel("WesterLife");
        title.setCssClass("action2");


        background.add(label);
        background.add(label2);
        background.add(title);

        add(background);
    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/notregistred.css"));
    }
}