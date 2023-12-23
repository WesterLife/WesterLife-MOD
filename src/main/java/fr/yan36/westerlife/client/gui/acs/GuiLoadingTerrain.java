package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiLoadingTerrain extends GuiFrame {



    public GuiLoadingTerrain(Boolean isServer) {
        super(new GuiScaler.Identity());
        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
//        background.getStyle().setBackgroundColor(0x000000);

        GuiLabel label = new GuiLabel("Chargement...");
        label.setCssClass("action");
//        label.getStyle().setBackgroundColor(0x000000);

        background.add(label);

        add(background);
    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}