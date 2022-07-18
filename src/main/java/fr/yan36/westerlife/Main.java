package fr.yan36.westerlife;

import fr.dynamx.api.contentpack.DynamXAddon;
import fr.yan36.westerlife.client.utils.creativetabs.WesterTab;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.blocks.*;
import fr.yan36.westerlife.common.items.ItemDynamx;
import fr.yan36.westerlife.common.network.Network;
import fr.yan36.westerlife.common.registry.RegisteringHandler;
import fr.yan36.westerlife.utils.Discord;
import fr.yan36.westerlife.utils.commands.WesterLifeCommand;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(
        modid = Main.MODID,
        name = Main.NAME,
        version = Main.VERSION,
        dependencies = "before: dynamxmod"
)
@DynamXAddon(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {

    /**
     * On déclare différentes valeurs utiles.
     */

    public static final String MODID = "westerlife";
    public static final String NAME = "WesterLife Mod";
    public static final String VERSION = "1.3";
    public static String DISCORD_ID, DRP_DETAILS, DRP_IMAGE_LARGE, DRP_IMAGE_LARGE_TEXT, DRP_IMAGE_SMALL, DRP_STATE_SOLO, DRP_STATE_MULTIPLAYER, DRP_STATE_OTHER;

    @Mod.Instance(Main.MODID)
    public static Main instance;
    public static SimpleNetworkWrapper network;

    public static ItemDynamx PistoletRadar;
    public static ItemDynamx Belier;
    public static ItemDynamx Menottes;
    public static BlockSignVillage SignVillage;
    public static BlockLaptop Laptop;
    public static BlockDistributeur Distributeur;
    public static BlockKeypad Keypad;
    public static BlockBisign doublefeurouge;
    public static BlockTerminalDePaiement TerminalDePaiement;
    public static BlockRadarFixe radarFixe;

    @DynamXAddon.AddonEventSubscriber
    public static void init() {

        PistoletRadar = (ItemDynamx) new ItemDynamx(Main.MODID, "pistoletradar", "pistoletradar/pistoletradar.obj").setMaxStackSize(1);
        Belier = (ItemDynamx) new ItemDynamx(Main.MODID, "belier", "belier/belier.obj").setMaxStackSize(1);
        Menottes = (ItemDynamx) new ItemDynamx(Main.MODID, "menottes", "menottes/menotte.obj").setMaxStackSize(1);

        Distributeur = new BlockDistributeur(Material.ROCK, Main.MODID, "distributeur", "atm/atm.obj");
        SignVillage = new BlockSignVillage(Material.ANVIL, Main.MODID, "panneauvillage", "signvillage/sign.obj");
        doublefeurouge = new BlockBisign(Material.ANVIL, Main.MODID, "doublefeurouge", "bisign/bicolor.obj");
//        feu_tricolore = new BlockDynamx(Material.ANVIL, Main.MODID, "feutricolore", "feut/feut.obj");

        Laptop = new BlockLaptop(Material.ANVIL, Main.MODID, "laptop", "laptop/pc.obj");
        TerminalDePaiement = new BlockTerminalDePaiement(Material.ANVIL, Main.MODID, "tdp", "tdp/paiement.obj");
        radarFixe = new BlockRadarFixe(Material.ANVIL, Main.MODID, "radarfixe", "radar/radarfixe.obj");

        //Keypad = new BlockKeypad(Material.ANVIL, Main.MODID, "keypad", "keypad/keypad.obj");
    }


    @SidedProxy(clientSide = "fr.yan36.westerlife.client.ClientProxy", serverSide = "fr.yan36.westerlife.server.ServerProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        proxy.preInit();
        logger = event.getModLog();
        Network.init();
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());

            DISCORD_ID = "835564028528033852";
            DRP_DETAILS = "WesterLife";
            DRP_IMAGE_LARGE = "logo_large";
            DRP_IMAGE_LARGE_TEXT = "Serveur Minecraft RôlePlay";
            DRP_IMAGE_SMALL = "head";
            DRP_STATE_SOLO = "En solo";
            DRP_STATE_MULTIPLAYER = "Connecté(e)";
            DRP_STATE_OTHER = "Dans les menus";
        if(event.getSide().isClient()) {
            new Discord().start();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        RegisteringHandler.initRegistries();
        PermissionAPI.registerNode("westerlife.command.wlmod", DefaultPermissionLevel.OP, "Permission d'administration");

    }

    @Mod.EventHandler
    public void onserverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new WesterLifeCommand());

    }




    public static final CreativeTabs creativeTab = new WesterTab("westertab");

}