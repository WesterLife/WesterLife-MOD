package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {
    /**
     * La honte
     */
    public static void init() {
        Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("westerlife1");
        Main.network.registerMessage(PacketCreateIdentity.handler.class, PacketCreateIdentity.class, 1, Side.CLIENT);
        Main.network.registerMessage(PacketCreateIdentityServer.ServerHandler.class, PacketCreateIdentityServer.class, 2, Side.SERVER);
        Main.network.registerMessage(PacketSyncPlayer.Handler.class, PacketSyncPlayer.class, 3, Side.CLIENT);
        Main.network.registerMessage(PacketCreatePlainte.Handler.class, PacketCreatePlainte.class, 4, Side.SERVER);
        Main.network.registerMessage(PacketRetirerArgentServer.ServerHandler.class, PacketRetirerArgentServer.class, 5, Side.SERVER);
        Main.network.registerMessage(PacketDepoArgentServer.ServerHandler.class, PacketDepoArgentServer.class, 6, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIAtm.Handler.class, PacketOpenGUIAtm.class, 7, Side.CLIENT);
        Main.network.registerMessage(PacketOpenGUIEditSign.Handler.class, PacketOpenGUIEditSign.class, 8, Side.CLIENT);
        Main.network.registerMessage(PacketUpdateTileSign.Handler.class, PacketUpdateTileSign.class, 9, Side.SERVER);
        Main.network.registerMessage(PacketReqSyncPlayer.Handler.class, PacketReqSyncPlayer.class, 10, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIGendarmerie.Handler.class, PacketOpenGUIGendarmerie.class, 10, Side.CLIENT);
    }
}
