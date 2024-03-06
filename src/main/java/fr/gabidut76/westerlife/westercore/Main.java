package fr.gabidut76.westerlife.westercore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.dynamx.api.contentpack.DynamXAddon;
import fr.dynamx.api.contentpack.object.render.Enum3DRenderLocation;
import fr.dynamx.api.dxmodel.DxModelPath;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.contentpack.loader.ObjectLoader;
import fr.dynamx.common.contentpack.loader.SubInfoTypesRegistry;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.contentpack.type.vehicle.VehicleValidator;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.dynamx.common.items.vehicle.ItemHelicopter;
import fr.dynamx.utils.debug.DynamXDebugOption;
import fr.dynamx.utils.debug.DynamXDebugOptions;
import fr.gabidut76.westerlife.CoreMod.WesterLifeSecurityManager;
import fr.gabidut76.westerlife.common.entities.npcbank.NPCBank;
import fr.gabidut76.westerlife.common.objects.gameplay.MacdoCommand;
import fr.gabidut76.westerlife.common.utils.commands.CommandWarn;
import fr.gabidut76.westerlife.westerradio.WesterRadio;
import fr.nathanael2611.simpledatabasemanager.core.Database;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.nathanael2611.simpledatabasemanager.core.SyncedDatabases;
import fr.gabidut76.westerlife.client.gui.GuiHandler;
import fr.gabidut76.westerlife.client.utils.creativetabs.WesterTab;
import fr.gabidut76.westerlife.westercore.proxies.CommonProxy;
import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.CO2ManagementThread;
import fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.ClothEntity;
import fr.gabidut76.westerlife.common.entities.DynamX.punchingball.TestEntity2;
import fr.gabidut76.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.gabidut76.westerlife.common.entities.EntitySeat;
import fr.gabidut76.westerlife.common.entities.NPCTestEntity;
import fr.gabidut76.westerlife.common.entities.npc.NPCConcessEntity;
import fr.gabidut76.westerlife.common.entities.npcdomac.NPCDomacEntity;
import fr.gabidut76.westerlife.common.handlers.RegistryHandler;
import fr.gabidut76.westerlife.common.init.*;
import fr.gabidut76.westerlife.common.utils.WesterBuiltinPack;
import fr.gabidut76.westerlife.common.utils.carmodule.AICarEngineModule;
import fr.gabidut76.westerlife.common.utils.carmodule.DamageCarModule;
import fr.gabidut76.westerlife.common.utils.carmodule.GarageModule;
import fr.gabidut76.westerlife.common.utils.commands.PersoCommand;
import fr.gabidut76.westerlife.common.utils.commands.WesterLifeCommand;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import net.minecraft.crash.CrashReport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static fr.gabidut76.westerlife.westercore.proxies.ClientProxy.discoverBindedTiles;
import static fr.gabidut76.westerlife.westercore.proxies.ClientProxy.discoverGuis;

@Mod(
        modid = Main.MODID,
        name = Main.NAME,
        version = Main.VERSION,
        dependencies = "before: dynamxmod; after: httpcore|httpclient|lavaplayer|music_westerlife|sdm|mcef|voicecgat;"

)
@DynamXAddon(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {

    //Util variables
    public static final String MODID = "westerlife";
    public static final String NAME = "WesterLife Mod";
    public static final String VERSION = "1.5.9-1";


    @Mod.Instance(Main.MODID)
    public static Main instance;
    public static SimpleNetworkWrapper network;

//    @SideOnly(Side.CLIENT)
//    public static BrowserScreen browserScreen;


    HashMap<Integer, DynamXItemArmor<?>> tqt_frere = new HashMap<>();

    public static Boolean isOpti = false;

    public static Database wl_db;

    public static CO2ManagementThread co2ManagementThread;



    @DynamXAddon.AddonEventSubscriber
    public static void init() {
        DynamXInit.init();
        ArmorInit.init();

        if (FMLCommonHandler.instance().getSide().isClient()) {
            DynamXContext.getDxModelRegistry().registerModel(new DxModelPath(new WesterBuiltinPack.WesterPackInfo(), new ResourceLocation(Main.MODID, "test.obj")));
            DynamXContext.getDxModelRegistry().registerModel(new DxModelPath(new WesterBuiltinPack.WesterPackInfo(), new ResourceLocation(Main.MODID, "punch.obj")));
            DynamXContext.getDxModelRegistry().registerModel(new DxModelPath(new WesterBuiltinPack.WesterPackInfo(), new ResourceLocation(Main.MODID, "models/dynamx/blocks/highroad/warningsign/warningsign.obj")));
            ItemInit.init();
            DynamXInit.WATER.getInfo().setItem3DRenderLocation(Enum3DRenderLocation.WORLD);
            DynamXInit.Taser.getInfo().setItem3DRenderLocation(Enum3DRenderLocation.WORLD);
            DynamXInit.PoteauRemote.getInfo().setItem3DRenderLocation(Enum3DRenderLocation.NONE);
            DynamXInit.barreChoco.getInfo().setItem3DRenderLocation(Enum3DRenderLocation.WORLD);
            DynamXInit.burger.getInfo().setItem3DRenderLocation(Enum3DRenderLocation.WORLD);
        }

    }

    @SidedProxy(clientSide = "fr.gabidut76.westerlife.westercore.proxies.ClientProxy", serverSide = "fr.gabidut76.westerlife.westercore.proxies.ServerProxy")
    public static CommonProxy proxy;

    public static Logger logger;
    public static DynamXDebugOption radar;
    public static DynamXDebugOption renderDebug;

    //    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void onserverStarting(FMLServerStartingEvent event) throws IOException {
        event.registerServerCommand(new WesterLifeCommand());
        event.registerServerCommand(new PersoCommand());
        event.registerServerCommand(new CommandWarn());
        co2ManagementThread = new CO2ManagementThread(Arrays.asList(event.getServer().worlds));
        System.out.println("Starting CO2 management thread");
        NemesisLink.init();
        co2ManagementThread.start();
        Databases.onServerStarting(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException, ClassNotFoundException, InstantiationException, LineUnavailableException {

        proxy.preInit();
        logger = event.getModLog();
        WesterLifeCommand.initModules();
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "testentity2"), TestEntity2.class, "testentity2", 2, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "npcai"), NPCTestEntity.class, "npcai", 3, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "entity_sit"), EntitySeat.class, "entity_sit", 4, this, 256, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "warningsign"), WarningSignEntity.class, "warningsign", 5, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "clothentity"), ClothEntity.class, "clothentity", 6, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "npcconcess"), NPCConcessEntity.class, "npcconcess", 7, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "npcdomac"), NPCDomacEntity.class, "npcdomac", 8, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "npcbank"), NPCBank.class, "npcbank", 9, this, 64, 1, true, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());




        // register world saved data
        Capabilities.init();
        Network.init(event.getSide());
        WesterRadio.init(event.getSide());
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        MinecraftForge.EVENT_BUS.register(this);
        radar = DynamXDebugOption.newOptionWithMask(DynamXDebugOptions.DebugCategories.GENERAL, "render radar debug", 32);
        renderDebug = DynamXDebugOption.newOptionWithMask(DynamXDebugOptions.DebugCategories.GENERAL, "render debug", 64);
        //warn: Discord RPC must be reimplemented
        if (event.getSide().isClient()) {

            // check if account is premium
            try {
//                new Discord().start();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        wl_db = Databases.getDatabase("westerlife_armorsuperposition");
        SyncedDatabases.add("westerlife_armorsuperposition");

        List<DynamXItemArmor<?>> globalitems = ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof DynamXItemArmor<?>).collect(Collectors.toCollection(ArrayList::new)).stream().map(e -> (DynamXItemArmor<?>) e.getValue()).collect(Collectors.toList());
        for (DynamXItemArmor<?> item : globalitems) {
            DynamXInit.fastRegistryAccess.put(item.getInfo().getFullName(), item);
            System.out.println("added " + item.getInfo().getFullName() + " to fast registry access");
        }

        File file = new File("launcher_profiles.json");
        if (!file.exists()) {
            CrashReport.makeCrashReport(new Exception("Launcher profiles not found"), "Launcher profiles not found");
        } else {
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
                JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

                if(obj.has("profiles")) {
                    logger.info("Skipping launcher profiles check");
                    isOpti = true;
                } else {
                    isOpti = obj.get("isOpti").getAsBoolean();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }


    @SubscribeEvent
    public void initVehicleModules(PhysicsEntityEvent.CreateModules<BaseVehicleEntity> event) {
        BaseVehicleEntity<?> entity = event.getEntity();
        event.getModuleList().add(new AICarEngineModule(entity));
        event.getModuleList().add(new GarageModule(entity));
        event.getModuleList().add(new DamageCarModule(entity));
        System.out.println("initVehicleModules");
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws LineUnavailableException {
        proxy.init();
        PermissionAPI.registerNode("westerlife.command.wlmod", DefaultPermissionLevel.OP, "Permission d'administration");
        PermissionAPI.registerNode("westerlife.command.notif", DefaultPermissionLevel.OP, "/notif");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) throws LineUnavailableException {

        System.out.println(DynamXObjectLoaders.ARMORS.getInfos());
//        MacdoCommand.init();
//        MacdoCommand.init();


//        SoundHandler soundManager = Minecraft.getMinecraft().getSoundHandler();
        // get livestream at

    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        if(event.getSide().isClient()) {
            discoverGuis(event);
            discoverBindedTiles(event);
            WesterLifeSecurityManager.makesecurity();
        }
        CommonProxy.discoverTiles(event);
    }

    public static final CreativeTabs WESTER_MAIN = new WesterTab("westertab");
    public static final CreativeTabs WESTER_ROADS = new WesterTab("westertab_roads");
    public static final CreativeTabs WESTER_CARDS = new WesterTab("westertab_cards");
    public static final CreativeTabs WESTER_ECO = new WesterTab("westertab_economy");
    public static final CreativeTabs WESTER_FOOD = new WesterTab("westertab_food");
    public static final CreativeTabs WESTER_STAFF = new WesterTab("westertab_staff");
    public static final CreativeTabs WESTER_GEND = new WesterTab("westertab_gend");
    public static final CreativeTabs WESTER_ILLEGAL = new WesterTab("westertab_illegal");


}