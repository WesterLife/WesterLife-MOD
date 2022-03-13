package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {
    /**
     * Side. signifie la side visé
     */
    public static void init() {
        Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("westerlife1");
        Main.network.registerMessage(PacketCreateIdentity.handler.class,PacketCreateIdentity.class,1, Side.CLIENT);
        Main.network.registerMessage(PacketCreateIdentityServer.ServerHandler.class,PacketCreateIdentityServer.class,2, Side.SERVER);
        Main.network.registerMessage(PacketSyncPlayer.Handler.class,PacketSyncPlayer.class,3, Side.CLIENT);
    }
}
