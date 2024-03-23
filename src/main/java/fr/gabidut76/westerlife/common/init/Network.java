package fr.gabidut76.westerlife.common.init;

import fr.gabidut76.westerlife.common.network.kits.PacketCreateKit;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncPlayerStats;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncChunk;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncExtraItem;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncGarage;
import fr.gabidut76.westerlife.common.network.*;
import fr.gabidut76.westerlife.common.network.garage.PacketExtractFromGarage;
import fr.gabidut76.westerlife.common.network.garage.PacketPutCarInGarage;
import fr.gabidut76.westerlife.common.network.old.*;
import fr.gabidut76.westerlife.common.network.sync.PacketAnimationToAll;
import fr.gabidut76.westerlife.common.network.sync.PacketClothToAll;
import fr.gabidut76.westerlife.common.network.sync.PacketSetEntityData;
import fr.gabidut76.westerlife.common.network.sync.PacketSyncClothes;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Network {

    public static void init(Side side) {
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
        Main.network.registerMessage(PacketChangeBlockColor.Handler.class, PacketChangeBlockColor.class, 31, Side.SERVER);
        Main.network.registerMessage(PacketSetKit.Handler.class, PacketSetKit.class, 32, Side.SERVER);

        Main.network.registerMessage(PacketUpdateTileEntity.Handler.class, PacketUpdateTileEntity.class, 34, Side.SERVER);
        Main.network.registerMessage(PacketPutCarInGarage.Handler.class, PacketPutCarInGarage.class, 35, Side.SERVER);
        Main.network.registerMessage(PacketUpdateMacdo.Handler.class, PacketUpdateMacdo.class, 36, Side.SERVER);
        Main.network.registerMessage(PacketExtractFromGarage.Handler.class, PacketExtractFromGarage.class, 37, Side.SERVER);
        Main.network.registerMessage(PacketSetEntityData.Handler.class, PacketSetEntityData.class, 47, Side.SERVER);
        if(side.isClient()) {
            Main.network.registerMessage(PacketSyncPlayerStats.ClientHandler.class, PacketSyncPlayerStats.class, 38, Side.CLIENT);
            Main.network.registerMessage(PacketSyncGarage.ClientHandler.class, PacketSyncGarage.class, 39, Side.CLIENT);
            Main.network.registerMessage(PacketSyncExtraItem.ClientHandler.class, PacketSyncExtraItem.class, 40, Side.CLIENT);
            Main.network.registerMessage(PacketSyncChunk.ClientHandler.class, PacketSyncChunk.class, 41, Side.CLIENT);
            Main.network.registerMessage(fr.gabidut76.westerlife.common.network.PacketSendCharacter.ClientHandler.class, fr.gabidut76.westerlife.common.network.PacketSendCharacter.class, 42, Side.SERVER);
            Main.network.registerMessage(PacketATMInteraction.ClientHandler.class, PacketATMInteraction.class, 43, Side.CLIENT);
            Main.network.registerMessage(PacketOpenConcessionaire.ClientHandler.class, PacketOpenConcessionaire.class, 44, Side.CLIENT);
            Main.network.registerMessage(PacketOpenGuiWithObject.ClientHandler.class, PacketOpenGuiWithObject.class, 45, Side.CLIENT);
            Main.network.registerMessage(PacketTakeMacdoCommand.ClientHandler.class, PacketTakeMacdoCommand.class, 46, Side.CLIENT);
            Main.network.registerMessage(PacketReqOpenInv.ClientHandler.class, PacketReqOpenInv.class, 47, Side.CLIENT);
            Main.network.registerMessage(PacketSendNotif.ClientHandler.class, PacketSendNotif.class, 48, Side.CLIENT);
            Main.network.registerMessage(PacketNotif.ClientHandler.class, PacketNotif.class, 49, Side.CLIENT);
            Main.network.registerMessage(PacketOpenGuiECO.ClientHandler.class, PacketOpenGuiECO.class, 50, Side.CLIENT);
            Main.network.registerMessage(PacketCreateKit.ClientHandler.class, PacketCreateKit.class, 51, Side.CLIENT);

        } else  {
            Main.network.registerMessage(PacketSyncPlayerStats.ServerHandler.class, PacketSyncPlayerStats.class, 38, Side.SERVER);
            Main.network.registerMessage(PacketSyncGarage.ServerHandler.class, PacketSyncGarage.class, 39, Side.SERVER);
            Main.network.registerMessage(PacketSyncExtraItem.ServerHandler.class, PacketSyncExtraItem.class, 40, Side.SERVER);
            Main.network.registerMessage(PacketSyncChunk.ServerHandler.class, PacketSyncChunk.class, 41, Side.SERVER);
            Main.network.registerMessage(fr.gabidut76.westerlife.common.network.PacketSendCharacter.ServerHandler.class, fr.gabidut76.westerlife.common.network.PacketSendCharacter.class, 42, Side.SERVER);
            Main.network.registerMessage(PacketATMInteraction.ServerHandler.class, PacketATMInteraction.class, 43, Side.SERVER);
            Main.network.registerMessage(PacketOpenConcessionaire.ServerHandler.class, PacketOpenConcessionaire.class, 44, Side.SERVER);
            Main.network.registerMessage(PacketOpenGuiWithObject.ServerHandler.class, PacketOpenGuiWithObject.class, 45, Side.SERVER);
            Main.network.registerMessage(PacketTakeMacdoCommand.ServerHandler.class, PacketTakeMacdoCommand.class, 46, Side.SERVER);
            Main.network.registerMessage(PacketReqOpenInv.ServerHandler.class, PacketReqOpenInv.class, 47, Side.SERVER);
            Main.network.registerMessage(PacketSendNotif.ServerHandler.class, PacketSendNotif.class, 48, Side.SERVER);
            Main.network.registerMessage(PacketNotif.ServerHandler.class, PacketNotif.class, 49, Side.SERVER);
            Main.network.registerMessage(PacketOpenGuiECO.ServerHandler.class, PacketOpenGuiECO.class, 50, Side.SERVER);
            Main.network.registerMessage(PacketCreateKit.ServerHandler.class, PacketCreateKit.class, 51, Side.SERVER);

        }


    }
}
