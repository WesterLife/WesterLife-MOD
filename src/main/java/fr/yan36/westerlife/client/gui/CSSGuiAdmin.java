package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiSlider;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.layout.PanelLayout;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.style.ComponentStyleManager;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class CSSGuiAdmin extends GuiFrame {
    public CSSGuiAdmin() {

        super(new GuiScaler.Identity());

        style.setBackgroundColor(new Color(152, 152, 152, 50).getRGB());
        setCssClass("home");

        GuiTabbedPane pane = new GuiTabbedPane();
        pane.setCssClass("tabbed_pane").getStyle().setBackgroundColor(new Color(84, 84, 82, 20).getRGB());

        GuiPanel warps = new GuiPanel();

        GuiPanel players = new GuiPanel(0, 0, 0, 0);
        GuiPanel settings = new GuiPanel(0, 0, 0, 0);
        GuiPanel others = new GuiPanel(0, 0, 0, 0);

        //Grid Layout;

        pane.addTab("Warps", warps);
        pane.addTab("Joueurs", players);
        pane.addTab("Paramètres", settings);
        pane.addTab("Autres", others);

        warps.setCssId("pane").setCssId("warp");
        players.setCssId("pane");
        settings.setCssId("pane");
        others.setCssId("pane");

        GuiScrollPane warpPane = new GuiScrollPane();
        warps.add(warpPane);

        add(pane);
        add(warps);
        add(players);
        add(settings);
        add(others);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/admin.css"));
    }
}
