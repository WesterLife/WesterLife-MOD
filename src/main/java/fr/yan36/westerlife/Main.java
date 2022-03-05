package fr.yan36.westerlife;

import fr.yan36.westerlife.creativetabs.WesterTab;
import fr.yan36.westerlife.proxy.CommonProxy;
import fr.yan36.westerlife.registry.RegisteringHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.script.ScriptManager;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

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

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit();

    }

    public Main() {
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
    }

    public static final WesterTab creativeTab = new WesterTab();

}
