package fr.yan36.westerlife.utils.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;

public class Discord {

    public static String DISCORD_ID, DRP_DETAILS, DRP_IMAGE_LARGE, DRP_IMAGE_LARGE_TEXT, DRP_IMAGE_SMALL, DRP_STATE_SOLO, DRP_STATE_MULTIPLAYER, DRP_STATE_OTHER;
    public final DiscordRPC client = DiscordRPC.INSTANCE;
    public final DiscordRichPresence richpresence = new DiscordRichPresence();
    Minecraft mc = Minecraft.getMinecraft();

    public void start() {
        DiscordEventHandlers event = new DiscordEventHandlers();

        DISCORD_ID = "835564028528033852";
        DRP_DETAILS = "Le serveur rôle-play !";
        DRP_IMAGE_LARGE = "logo_large";
        DRP_IMAGE_LARGE_TEXT = "Serveur Minecraft RôlePlay";
        DRP_IMAGE_SMALL = "head";
        DRP_STATE_SOLO = "En solo";
        DRP_STATE_MULTIPLAYER = "Connecté(e)";
        DRP_STATE_OTHER = "Dans les menus";

        this.client.Discord_Initialize(DISCORD_ID, event, true, "0");
        this.richpresence.startTimestamp = System.currentTimeMillis() / 1000;
        this.richpresence.details = DRP_DETAILS;
        this.richpresence.largeImageKey = DRP_IMAGE_LARGE;
        this.richpresence.largeImageText = DRP_IMAGE_LARGE_TEXT;
        this.richpresence.smallImageKey = DRP_IMAGE_SMALL;
        this.richpresence.partySize = 0;
        this.richpresence.partyMax = 0;
        this.richpresence.smallImageText = mc.getSession().getUsername();
        this.client.Discord_UpdatePresence(richpresence);

        new Thread("RPC-Callback-Handler") {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    client.Discord_UpdatePresence(richpresence);
                    client.Discord_RunCallbacks();
                    try {
                        if (mc.isSingleplayer()) {
                            richpresence.state = DRP_STATE_SOLO;
                            richpresence.partySize = 0;
                            richpresence.partyMax = 0;
                            client.Discord_UpdatePresence(richpresence);
                        }
                        else if (mc.world !=null && mc.getConnection() != null) {
                            richpresence.state = DRP_STATE_MULTIPLAYER;
                            richpresence.partySize = mc.getConnection().getPlayerInfoMap().size();
                            richpresence.partyMax = mc.getConnection().currentServerMaxPlayers;
                            client.Discord_UpdatePresence(richpresence);
                        } else {
                            richpresence.state = DRP_STATE_OTHER;
                            richpresence.partySize = 0;
                            richpresence.partyMax = 0;
                            client.Discord_UpdatePresence(richpresence);
                        }
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                        client.Discord_Shutdown();
                    }
                }
            }
        }.start();
    }
}
