package fr.yan36.westerlife.client.gui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.client.utils.News;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.ResourceLocation;

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

        // register  news

        Gson gson = new Gson();


        URL url = new URL("https://cdn.westerlife.fr/news/news.news");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        System.out.println(json.toString());
        reader.close();

        json = new StringBuilder(json.toString());
        System.out.println(json);
        JsonObject jsonObject = gson.fromJson(json.toString(), JsonObject.class);
        News news = new News(jsonObject.get("title").getAsString(), jsonObject.get("content").getAsString(), jsonObject.get("author").getAsString(), jsonObject.get("abouturl").getAsString());

        System.out.println(news.getTitle());
        System.out.println(news.getContent());
        System.out.println(news.getAuthor());
        System.out.println(news.getAbouturl());



        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
        background.setCssId("background");

        GuiPanel topbar = new GuiPanel();
        topbar.setCssClass("topbar");

        GuiPanel leftbar = new GuiPanel();
        leftbar.setCssClass("leftbar");

        GuiPanel logo = new GuiPanel();
        logo.setCssClass("logo");

        GuiPanel play = new GuiPanel();
        play.setCssClass("play");

        GuiPanel discord = new GuiPanel();
        discord.setCssClass("discord");

        GuiPanel quit = new GuiPanel();
        quit.setCssClass("quit");

        GuiPanel desc = new GuiPanel();
        desc.setCssClass("desc");

        GuiPanel mute = new GuiPanel();
        mute.setCssClass("mute");

        GuiPanel topbar_color = new GuiPanel();
        topbar_color.setCssClass("topbar_color");

        GuiPanel site = new GuiPanel();
        site.add(new GuiLabel(0, 0, 0, 0, "Site Internet").setCssClass("site"));

        GuiPanel param = new GuiPanel();
        param.add(new GuiLabel(0, 0, 0, 0, "Paramètres").setCssClass("param"));

        GuiPanel zonestaff = new GuiPanel();
        zonestaff.add(new GuiLabel(0, 0, 0, 0, "Zone-Staff").setCssClass("zonestaff"));

        GuiPanel title = new GuiPanel();
        title.add(new GuiLabel("WesterLife").setCssClass("title"));

        topbar.add(topbar_color);
        topbar.add(logo);

        zonestaff.addClickListener((x, y, bu) -> {
            mc.displayGuiScreen(new GuiMultiplayer(this.getGuiScreen()));
            System.out.println("zonestaff");
        });


        topbar.add(title);
        topbar.add(site);
        topbar.add(param);

        leftbar.add(play);

        param.addClickListener((x, y, bu) -> {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiOptions(this.getGuiScreen(), mc.gameSettings));
            System.out.println("param");

        });

        site.addClickListener((x, y, bu) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://westerlife.fr/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("https://westerlife.fr/");
        });

        background.add(topbar);

        leftbar.add(play);
        leftbar.add(discord);
        leftbar.add(mute);
        leftbar.add(quit);

        quit.addClickListener((x, y, bu) -> {
            mc.shutdown();
        });

        discord.addClickListener((x,y,bu) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/ZUsYjXE3"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("https://discord.gg/ZUsYjXE3");
        });

        AtomicBoolean muteState = new AtomicBoolean(true);


        mute.addClickListener((x, y, bu) -> {
            if(muteState.get()) {
                muteState.set(false);
                mute.setCssClass("mute_on");
            } else {
                muteState.set(true);
                mute.setCssClass("mute");
            }
        });

        background.add(leftbar);
        background.add(desc);


        //        if(Minecraft.getMinecraft().getSession().getUsername().equals("yan36")) {

//        }
        topbar.add(zonestaff);

        play.addClickListener((x, y, bu) -> {
           connect("51.38.250.27",25739);
        });



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
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/mainmenu.css"));
    }
}
