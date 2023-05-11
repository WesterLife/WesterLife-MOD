package fr.yan36.westerlife;

import fr.dynamx.api.contentpack.DynamXAddon;
import fr.dynamx.utils.debug.DynamXDebugOption;
import fr.dynamx.utils.debug.DynamXDebugOptions;
import fr.yan36.westerlife.client.gui.mcef.BrowserHud;
import fr.yan36.westerlife.client.gui.mcef.BrowserScreen;
import fr.yan36.westerlife.client.utils.creativetabs.WesterTab;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.entities.TestEntity2;
import fr.yan36.westerlife.common.init.DynamxInit;
import fr.yan36.westerlife.common.init.Network;
import fr.yan36.westerlife.common.objects.entreprises.CompanyAssociation;
import fr.yan36.westerlife.common.objects.entreprises.CompanyBase;
import fr.yan36.westerlife.common.objects.entreprises.CompanySARL;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;
import fr.yan36.westerlife.common.registry.RegistryHandler;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import fr.yan36.westerlife.common.utils.discord.Discord;
import fr.yan36.westerlife.server.AuthSystem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod(
        modid = Main.MODID,
        name = Main.NAME,
        version = Main.VERSION,
        dependencies = "before: dynamxmod"
)
@DynamXAddon(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {

    //Util variables
    public static final String MODID = "westerlife";
    public static final String NAME = "WesterLife Mod";
    public static final String VERSION = "1.5.5";

    @Mod.Instance(Main.MODID)
    public static Main instance;
    public static SimpleNetworkWrapper network;

    @SideOnly(Side.CLIENT)
    public static BrowserScreen browserScreen;

    @SideOnly(Side.CLIENT)
    public static BrowserHud browserHud;

    public static Boolean isEnvDev = false;


    @DynamXAddon.AddonEventSubscriber
    public void init() {
        DynamxInit.init();

    }

    @SidedProxy(clientSide = "fr.yan36.westerlife.client.ClientProxy", serverSide = "fr.yan36.westerlife.server.ServerProxy")
    public static CommonProxy proxy;

    public static Logger logger;
    public static DynamXDebugOption radar;

//    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void onserverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new WesterLifeCommand());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        proxy.preInit();
        logger = event.getModLog();
        WesterLifeCommand.initModules();
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "seat"), TestEntity2.class, "test", 2, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        if(event.getSide().isClient() && event.getSourceFile().getName().endsWith(".jar") ||  (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment") || Objects.requireNonNull(Loader.instance().activeModContainer()).getSource().isFile()) isEnvDev = true;
        System.out.println("WesterLife is in dev mode: " + isEnvDev);
        Network.init();
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        radar = DynamXDebugOption.newOptionWithMask(DynamXDebugOptions.DebugCategories.GENERAL, "render radar debug", 32);
        //warn: Discord RPC must be reimplemented
        if(event.getSide().isClient()) {
            
            try {
                new Discord().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AuthSystem.init();
        }

        CompanyBase companyBase = new CompanyBase("test", "somewhere", 0, "someone");

        CompanyAssociation companyAssociation = new CompanyAssociation(companyBase, "test", "somewhere", "objective", 50f);
        System.out.println(companyAssociation.getBaseCompany().getCreationDate());

        List<Rank> ranks = new ArrayList<>();
        ranks.add(new Rank("grade1", "description", 1, 1));

        List<String> impots = new ArrayList<>();
        impots.add("impot1");
        impots.add("impot2");

        List<String> cars = new ArrayList<>();
        impots.add("voitureA");
        impots.add("voitureB");

        CompanyBase companyBase1 = new CompanyBase("test", "somewhere", 0, "someone");
        CompanySARL companySARL = new CompanySARL(companyBase1, "test", ranks, impots, cars);
        System.out.println(companySARL);
        System.out.println("bbbb");

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws LineUnavailableException {
        proxy.init();
        PermissionAPI.registerNode("westerlife.command.wlmod", DefaultPermissionLevel.OP, "Permission d'administration");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public static final CreativeTabs WESTER_MAIN = new WesterTab("westertab");
    public static final CreativeTabs WESTER_ROADS = new WesterTab("westertab_roads");



}