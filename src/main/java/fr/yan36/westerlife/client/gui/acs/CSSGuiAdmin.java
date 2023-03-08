package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

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

        GuiPanel warps = (GuiPanel) new GuiPanel().setCssId("pane");

        GuiPanel players = (GuiPanel) new GuiPanel(0, 0, 0, 0).setCssId("pane");

        GuiPanel settings = (GuiPanel) new GuiPanel(0, 0, 0, 0).setCssId("pane");

        GuiPanel others = (GuiPanel) new GuiPanel(0, 0, 0, 0).setCssId("pane");

        //Grid Layout ;

        pane.addTab("Warps", warps);
        pane.addTab("Joueurs", players);
        pane.addTab("Paramètres", settings);
        pane.addTab("Autres", others);

        warps.setCssId("pane");
        players.setCssId("pane");
        settings.setCssId("pane");
        others.setCssId("pane");

        GuiScrollPane pane1 = new GuiScrollPane().setAutoScroll(true);
        pane1.setLayout(new GridLayout(-1, 25, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            GuiPanel label = (GuiPanel) new GuiPanel().setCssClass("button");
            label.addClickListener((x,y,bu) -> {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Warp"));
                System.out.println("e");
                });
            pane1.add(label);
        }

        warps.add(pane1);

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
