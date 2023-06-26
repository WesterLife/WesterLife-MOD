package fr.yan36.westerlife.client.gui.acs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.utils.News;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CSSGuiMainMenu extends GuiFrame {
    public CSSGuiMainMenu() throws IOException {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel settings = new GuiPanel();
        settings.setCssClass("settings");

        settings.addClickListener((mouseX, mouseY, mouseButton) -> {
            try {
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(new CSSGuiMainMenu().getGuiScreen(), Minecraft.getMinecraft().gameSettings));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        background.add(settings);
        GuiPanel connect = new GuiPanel();
        connect.setCssClass("connect");

        connect.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                try {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new CSSGuiMainMenu().getGuiScreen()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiWorldSelection(new CSSGuiMainMenu().getGuiScreen()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GuiPanel exit = new GuiPanel();
        exit.setCssClass("exit");

        exit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().shutdown();
        });

        background.add(exit);

        background.add(connect);

        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");

        background.add(perf);


        add(background);

        if (this.networkManager != null)
        {
            if (this.networkManager.isChannelOpen())
            {
                this.networkManager.processReceivedPackets();
            }
            else
            {
                this.networkManager.handleDisconnection();
            }
        }
    }

    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private NetworkManager networkManager;
    private void connect(final String ip, final int port)
    {
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {

                    inetaddress = InetAddress.getByName(ip);
                    networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, mc.gameSettings.isUsingNativeTransport());
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, mc,new CSSGuiMainMenu().getGuiScreen()));
                    networkManager.sendPacket(new C00Handshake(ip, port, EnumConnectionState.LOGIN, true));
                    networkManager.sendPacket(new CPacketLoginStart(mc.getSession().getProfile()));
                }
                catch (UnknownHostException unknownhostexception)
                {
                    try {
                        mc.displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception exception)
                {

                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }
                    try {
                        mc.displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/mainmenu.css"));
    }
}
