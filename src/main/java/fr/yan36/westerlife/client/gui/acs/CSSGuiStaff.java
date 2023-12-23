package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.yan36.westerlife.common.blocks.tileentity.*;
import fr.yan36.westerlife.common.network.PacketUpdateTileEntity;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
