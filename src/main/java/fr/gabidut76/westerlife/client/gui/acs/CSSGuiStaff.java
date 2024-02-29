package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSGuiStaff extends GuiFrame {
    public CSSGuiStaff() {
        super(new GuiScaler.Identity());

        GuiTabbedPane tabbedPane = new GuiTabbedPane();

        GuiPanel bac = new GuiPanel();

        bac.setCssClass("bac");

        GuiPanel homePanel = new GuiPanel();

        GuiLabel title = new GuiLabel("§cWest§4Life");
        title.setCssClass("title");
        homePanel.add(title);

        tabbedPane.addTab("§cAcceuil", homePanel);


        GuiPanel selector = new GuiPanel();
        selector.setCssClass("selector");
        selector.setLayout(GridLayout.columnLayout(0.09f, 0.01f));


        tabbedPane.getTabButton(0).addClickListener((mouseX, mouseY, mouseButton) -> {
            System.out.println("click");
        });
        tabbedPane.getTabButton(0).setCssClass("button");
        tabbedPane.getStyle().setZLevel(1000);
        selector.add(tabbedPane.getTabButton(0));

        GuiButton identity = new GuiButton("§cIdentité");
        tabbedPane.selectTab(1);
        identity.setCssClass("button");
        selector.add(identity);


        tabbedPane.setCssClass("tabbedpane");
        bac.add(tabbedPane);
        bac.add(selector);

        add(bac);


    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("westerlife:acsgui/staff.css"));
    }
}
