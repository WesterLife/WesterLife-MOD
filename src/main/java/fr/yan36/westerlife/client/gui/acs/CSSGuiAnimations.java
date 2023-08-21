package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.nathanael2611.modularvoicechat.client.gui.GuiConfig;
import fr.nathanael2611.modularvoicechat.client.voice.audio.MicroManager;
import fr.nathanael2611.modularvoicechat.client.voice.audio.SpeakerManager;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketAnimation;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CSSGuiAnimations extends GuiFrame {
    private static int currentId = 0;
    public CSSGuiAnimations() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        int i = 0;

        for (Animation animation : Animation.values()) {
            if(animation == Animation.SLEEP) continue;

            System.out.println("Adding animation " + animation.getId() + " to gui");

            currentId = animation.getId();
            GuiScrollPane animationPanel = new GuiScrollPane();
            animationPanel.setLayout(new GridLayout(-1,-1,1, GridLayout.GridDirection.HORIZONTAL, 1));
            animationPanel.setCssClass("animation");

            GuiLabel animationLabel = new GuiLabel(Objects.equals(animation.getName(), "") ? "Aucune" : animation.getName());
            animationLabel.setCssClass("animationLabel");
            animationPanel.add(animationLabel);

            animationPanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Main.network.sendToServer(new PacketAnimation(animation));
                System.out.println("Sending animation " + animation + " to server");
            });
            if(animation == Animation.NONE) animationLabel.getStyle().setFontColor(TextFormatting.RED);


            animationPanel.getStyle().setOffsetY(25 * i);
            animationPanel.getStyle().setZLevel(1000);
            i++;
            background.add(animationPanel);
        }

        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");
        add(perf);

        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/animations.css"));
    }
}
