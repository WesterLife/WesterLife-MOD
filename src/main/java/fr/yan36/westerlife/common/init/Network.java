package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketAskToCreateCharacter;
import fr.yan36.westerlife.common.network.PacketCreateCharacter;
import fr.yan36.westerlife.common.network.PacketOpenMcefGui;
import fr.yan36.westerlife.common.network.old.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {

    public static void init() {
        Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("westerlife1");
        Main.network.registerMessage(PacketAskToCreateCharacter.handler.class, PacketAskToCreateCharacter.class, 1, Side.CLIENT);
        Main.network.registerMessage(PacketCreateCharacter.ServerHandler.class, PacketCreateCharacter.class, 2, Side.SERVER);
        Main.network.registerMessage(PacketSyncPlayer.Handler.class, PacketSyncPlayer.class, 3, Side.CLIENT);
        Main.network.registerMessage(PacketCreatePlainte.Handler.class, PacketCreatePlainte.class, 4, Side.SERVER);
        Main.network.registerMessage(PacketRetirerArgentServer.ServerHandler.class, PacketRetirerArgentServer.class, 5, Side.SERVER);
        Main.network.registerMessage(PacketDepoArgentServer.ServerHandler.class, PacketDepoArgentServer.class, 6, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIAtm.Handler.class, PacketOpenGUIAtm.class, 7, Side.CLIENT);
        Main.network.registerMessage(PacketOpenGUIEditSign.Handler.class, PacketOpenGUIEditSign.class, 8, Side.CLIENT);
        Main.network.registerMessage(PacketUpdateTileSign.Handler.class, PacketUpdateTileSign.class, 9, Side.SERVER);
        Main.network.registerMessage(PacketReqSyncPlayer.Handler.class, PacketReqSyncPlayer.class, 10, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIGendarmerie.Handler.class, PacketOpenGUIGendarmerie.class, 11, Side.CLIENT);
        Main.network.registerMessage(PacketLoginGendarmerieServer.Handler.class, PacketLoginGendarmerieServer.class, 12, Side.SERVER);
        Main.network.registerMessage(PacketLoginGendarmerie.Handler.class, PacketLoginGendarmerie.class, 13, Side.CLIENT);
        Main.network.registerMessage(PacketOpenGUIKeypad.Handler.class, PacketOpenGUIKeypad.class, 14, Side.CLIENT);
        Main.network.registerMessage(PacketLoginPompierServer.Handler.class, PacketLoginPompierServer.class, 15, Side.SERVER);
        Main.network.registerMessage(PacketLoginPompier.Handler.class, PacketLoginPompier.class, 16, Side.CLIENT);
        Main.network.registerMessage(PacketOpenGUIGendarmerieServer.Handler.class, PacketOpenGUIGendarmerieServer.class, 17, Side.SERVER);
        Main.network.registerMessage(PacketChangerCodeServer.Handler.class, PacketChangerCodeServer.class, 18, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIAdmin.Handler.class, PacketOpenGUIAdmin.class, 19, Side.SERVER);
        Main.network.registerMessage(PacketATMTransaction.Handler.class, PacketATMTransaction.class, 20, Side.SERVER);
        Main.network.registerMessage(PacketOpenMcefGui.Handler.class, PacketOpenMcefGui.class, 21, Side.CLIENT);
    }
}
