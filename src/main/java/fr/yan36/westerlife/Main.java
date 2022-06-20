package fr.yan36.westerlife;

import fr.dynamx.api.contentpack.DynamXAddon;
import fr.yan36.westerlife.client.creativetabs.WesterTab;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.blocks.BlockKeypad;
import fr.yan36.westerlife.common.blocks.BlockLaptop;
import fr.yan36.westerlife.common.blocks.BlockSignVillage;
import fr.yan36.westerlife.common.items.ItemDynamx;
import fr.yan36.westerlife.common.network.Network;
import fr.yan36.westerlife.common.registry.RegisteringHandler;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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
    public static final String VERSION = "1.0";
    public static String DISCORD_ID, DRP_DETAILS, DRP_IMAGE_LARGE, DRP_IMAGE_LARGE_TEXT, DRP_IMAGE_SMALL, DRP_STATE_SOLO, DRP_STATE_MULTIPLAYER, DRP_STATE_OTHER;

    @Mod.Instance(Main.MODID)
    public static Main instance;
    public static SimpleNetworkWrapper network;

    public static ItemDynamx PistoletRadar;
    public static BlockSignVillage SignVillage;
    public static BlockLaptop Laptop;
    public static BlockKeypad Keypad;

    @DynamXAddon.AddonEventSubscriber
    public static void init() {

        PistoletRadar = (ItemDynamx) new ItemDynamx(Main.MODID, "pistoletradar", "pistoletradar/pistoletradar.obj").setMaxStackSize(1);
        SignVillage = new BlockSignVillage(Material.ANVIL, Main.MODID, "panneauvillage", "signvillage/sign.obj");

        //radar = new BlockDynamx(Material.ANVIL, Main.MODID, "radar", "radar/radar.obj");
        //feu_tricolore = new BlockDynamx(Material.ANVIL, Main.MODID, "feutricolore", "feut/feut.obj");
        Laptop = new BlockLaptop(Material.ANVIL, Main.MODID, "laptop", "laptop/ordi.obj");
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
        //if(event.getSide().isClient()) {
            //new Discord();
        //}
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        RegisteringHandler.initRegistries();
    }



    public static final CreativeTabs creativeTab = new WesterTab("westertab");

}