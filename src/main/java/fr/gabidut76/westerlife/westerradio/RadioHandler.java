package fr.gabidut76.westerlife.westerradio;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import fr.gabidut76.westerlife.common.network.PacketSendNotif;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.List;
@ForgeVoicechatPlugin
public class RadioHandler implements VoicechatPlugin {

    public static VoicechatServerApi api;

    public static HashMap<String, Group> frequencyMap = new HashMap<>();
    @Override
    public void initialize(VoicechatApi api) {
        System.out.println("WesterRadio Initialized");
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted, 100);
    }

    @Override
    public String getPluginId() {
        return "WesterRadio";
    }

    public void onServerStarted(VoicechatServerStartedEvent e) {
        api = e.getVoicechat();
    }

    public static void connectPlayerToFrequency(String frequency, EntityPlayer player) {
        if (frequencyMap.containsKey(frequency)) {
//            frequencyMap.get(frequency).
            VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
            if (connection != null) {
                Group g = frequencyMap.get(frequency);
                connection.setGroup(g);
                Main.network.sendTo(new PacketSendNotif(new Notification("Radio", "Connecté à la fréquence : " + frequency,0x00FF00, System.currentTimeMillis())), (EntityPlayerMP) player);
            }
        } else {
            Group g = api.groupBuilder()
                    .setName(frequency)
                    .setPersistent(false)
                    .setType(Group.Type.NORMAL)
                    .setPassword("password")
                    .build();
            frequencyMap.put(frequency, g);

            VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
            if (connection != null) {
                connection.setGroup(g);
                Main.network.sendTo(new PacketSendNotif(new Notification("Radio", "Connecté à la fréquence : " + frequency,0x00FF00, System.currentTimeMillis())), (EntityPlayerMP) player);
            }
        }

        System.out.println("Player " + player.getName() + " connected to frequency " + frequency);

    }

    public static void disconnectPlayerFromFrequency(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            connection.setGroup(null);
            Main.network.sendTo(new PacketSendNotif(new Notification("Radio", "Déconnecté de la fréquence.",0xFF0000, System.currentTimeMillis())), (EntityPlayerMP) player);
        }
    }
}
