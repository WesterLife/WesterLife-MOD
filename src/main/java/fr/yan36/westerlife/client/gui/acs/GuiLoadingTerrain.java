package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiLoadingTerrain extends GuiFrame {

    public GuiLoadingTerrain() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("WesterLife");
        title.setCssClass("title");

        AtomicInteger p = new AtomicInteger();
        GuiLabel action = new GuiLabel("Connection au serveur");
        action.setCssClass("action");
        action.addTickListener(() -> {
           if(Minecraft.getMinecraft().world.getWorldTime() % 10 == 0) {
                action.setText(action.getText() + ".");
                if(p.get() >= 3) {
                    action.setText("Connection au serveur");
                    p.set(0);
                } else {
                    p.getAndIncrement();
                }
           }
        });


        GuiLabel mention = new GuiLabel("WesterLife n'est pas affilié à Mojang AB.");
        mention.setCssClass("mention");


        background.add(title);
        background.add(action);
        background.add(mention);

        add(background);
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}