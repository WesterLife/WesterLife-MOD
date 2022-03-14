package fr.yan36.westerlife;

import fr.dynamx.api.contentpack.DynamXAddon;
import fr.yan36.westerlife.client.creativetabs.WesterTab;
import fr.yan36.westerlife.common.network.Network;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.registry.RegisteringHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

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

    @DynamXAddon.AddonEventSubscriber
    public static void init() {
    }

    @SidedProxy(clientSide = "fr.yan36.westerlife.client.ClientProxy", serverSide = "fr.yan36.westerlife.serveur.ServerProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event.getSuggestedConfigurationFile());
        logger = event.getModLog();
        Network.init();
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());

            DISCORD_ID = "835564028528033852";
            DRP_DETAILS = "WesterLife";
            DRP_IMAGE_LARGE = "logo_large";
            DRP_IMAGE_LARGE_TEXT = "Serveur Minecraft RôlePlay";
            DRP_IMAGE_SMALL = "logo_large";
            DRP_STATE_SOLO = "En solo";
            DRP_STATE_MULTIPLAYER = "Connecté(e)";
            DRP_STATE_OTHER = "Dans les menus";

            if(Side.CLIENT.isClient()) {
                new Discord().start();
                System.out.println("WesterLife >> Initialisation de RPC");
            }
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    public static final CreativeTabs creativeTab = new WesterTab("westertab");

}
