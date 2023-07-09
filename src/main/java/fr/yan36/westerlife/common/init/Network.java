package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.*;
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
        Main.network.registerMessage(PacketReqSyncPlayer.Handler.class, PacketReqSyncPlayer.class, 10, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIKeypad.Handler.class, PacketOpenGUIKeypad.class, 14, Side.CLIENT);
        Main.network.registerMessage(PacketChangerCodeServer.Handler.class, PacketChangerCodeServer.class, 18, Side.SERVER);
        Main.network.registerMessage(PacketOpenGUIAdmin.Handler.class, PacketOpenGUIAdmin.class, 19, Side.SERVER);
        Main.network.registerMessage(PacketATMTransaction.Handler.class, PacketATMTransaction.class, 20, Side.SERVER);
        Main.network.registerMessage(PacketOpenMcefGui.Handler.class, PacketOpenMcefGui.class, 21, Side.CLIENT);
        Main.network.registerMessage(PacketAnimation.Handler.class, PacketAnimation.class, 22, Side.SERVER);
        Main.network.registerMessage(PacketAnimationToAll.Handler.class, PacketAnimationToAll.class, 23, Side.CLIENT);
        Main.network.registerMessage(PacketTryCode.Handler.class, PacketTryCode.class, 24, Side.SERVER);
        Main.network.registerMessage(PacketPlaySound.Handler.class, PacketPlaySound.class, 25, Side.CLIENT);
        Main.network.registerMessage(PacketSyncClothes.Handler.class, PacketSyncClothes.class, 26, Side.SERVER);
        Main.network.registerMessage(PacketClothToAll.Handler.class, PacketClothToAll.class, 27, Side.CLIENT);
        Main.network.registerMessage(PacketOpenAcsGui.Handler.class, PacketOpenAcsGui.class, 28, Side.CLIENT);
        Main.network.registerMessage(PacketRequestCharacter.Handler.class, PacketRequestCharacter.class, 29, Side.SERVER);
        Main.network.registerMessage(PacketSendCharacter.Handler.class, PacketSendCharacter.class, 30, Side.CLIENT);
        Main.network.registerMessage(PacketChangeBlockColor.Handler.class, PacketChangeBlockColor.class, 31, Side.SERVER);
        Main.network.registerMessage(PacketSetKit.Handler.class, PacketSetKit.class, 32, Side.SERVER);

    }
}
