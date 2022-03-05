package fr.yan36.westerlife;

import fr.yan36.westerlife.creativetabs.WesterTab;
import fr.yan36.westerlife.proxy.CommonProxy;
import fr.yan36.westerlife.registry.RegisteringHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {

    // On déclare différentes valeurs utiles.

    public static final String MODID = "westerlife";
    public static final String NAME = "WesterLife Mod";
    public static final String VERSION = "1.1";

    @Mod.Instance(Main.MODID)
    public static Main instance;

    @SidedProxy(clientSide = "fr.yan36.westerlife.proxy.ClientProxy", serverSide = "fr.yan36.westerlife.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    public Main() {
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
    }

    public static final WesterTab creativeTab = new WesterTab();

}
