package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiServerLogin extends GuiFrame {

    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private NetworkManager networkManager;
    private final GuiScreen previousGuiScreen;

    public GuiServerLogin(GuiScreen previousGuiScreen) {
        super(new GuiScaler.Identity());
        this.previousGuiScreen = previousGuiScreen;

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
    public void tick() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            } else {
                this.networkManager.handleDisconnection();
            }
        }
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}