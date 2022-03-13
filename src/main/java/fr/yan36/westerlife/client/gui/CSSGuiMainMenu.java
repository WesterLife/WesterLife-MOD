package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CSSGuiMainMenu extends GuiFrame {
    public CSSGuiMainMenu() {
        super(new GuiScaler.AdjustToScreenSize(true,1,1));
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");

        GuiPanel para = new GuiPanel();
        para.setCssId("para").addClickListener((x, y, bu) -> {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiOptions(this.getGuiScreen(), mc.gameSettings));
        });
        screen.add(para);
        GuiPanel play = new GuiPanel();
        play.setCssId("play").addClickListener((x, y, bu) -> {
            connect("51.38.250.27",25739);
        });
        screen.add(play);
        GuiPanel site = new GuiPanel();
        site.setCssId("site").addClickListener((x, y, bu) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://westerlife.fr/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        screen.add(site);
        add(screen);


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
                    mc.displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());
                }
                catch (Exception exception)
                {

                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }
                    mc.displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());

                }
            }
        }).start();
    }
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/mainmenu.css"));
    }
}
