package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiProgressBar;
import javafx.scene.control.ProgressBar;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSGuiAnimation extends GuiFrame {
    public CSSGuiAnimation() {
        super(new GuiScaler.Identity());

        GuiPanel backGround = new GuiPanel();
        backGround.setCssClass("backGround");

        GuiPanel leverLesMains = new GuiPanel();
        leverLesMains.setCssClass("leverLesMains");

        GuiPanel gardeAVous = new GuiPanel();
        gardeAVous.setCssClass("gardeAVous");

        GuiPanel pointerDuDoigt = new GuiPanel();
        pointerDuDoigt.setCssClass("pointerDuDoigt");

        GuiPanel stopAnimation = new GuiPanel();
        stopAnimation.setCssClass("stopAnimation");

        backGround.add(leverLesMains);
        backGround.add(gardeAVous);
        backGround.add(pointerDuDoigt);
        backGround.add(stopAnimation);

        leverLesMains.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        gardeAVous.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        pointerDuDoigt.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        stopAnimation.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        add(backGround);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/animation.css"));
    }
}
