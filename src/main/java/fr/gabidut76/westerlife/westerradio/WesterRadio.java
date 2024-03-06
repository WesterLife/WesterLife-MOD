package fr.gabidut76.westerlife.westerradio;

import fr.gabidut76.westerlife.westerradio.packets.PacketRadioConnected;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class WesterRadio {
    public static SimpleNetworkWrapper radio_network;

    public static void init(Side side) {
        radio_network = new SimpleNetworkWrapper("wester_radio");

        if(side.isClient()) {
            radio_network.registerMessage(PacketRadioConnected.ClientHandler.class, PacketRadioConnected.class, 1, Side.CLIENT);
        }

        if(side.isServer()) {
            radio_network.registerMessage(PacketRadioConnected.ServerHandler.class, PacketRadioConnected.class, 1, Side.SERVER);
        }

    }
}
