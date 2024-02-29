package fr.gabidut76.westerlife.westercore.proxies;

import com.mojang.authlib.GameProfile;
import fr.gabidut76.westerlife.common.blocks.tileentity.*;
import fr.gabidut76.westerlife.common.handlers.SoundsHandler;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.util.*;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta) {

    }

    public void registerVariantRenderer(Item item, int meta, String filename, String id) {


    }

    public void registerEntityRenderers() {

    }

    public EntityPlayer loadPlayer(NBTTagCompound teamnbt, World world) {

        System.out.println("Loading player " + teamnbt.getString("name"));

        GameProfile profile = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache().getProfileByUUID(UUID.fromString(teamnbt.getString("uuid")));
        EntityPlayerMP member = new EntityPlayerMP(FMLCommonHandler.instance().getMinecraftServerInstance(), (WorldServer) world, profile, new PlayerInteractionManager(world));
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().setPlayerManager(new WorldServer[]{(WorldServer) world});
        member.deserializeNBT(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerNBT(member));
        member.mountEntityAndWakeUp();

        return member;

    }
    public static HashMap<Class<? extends TileEntitySyncClient>, ResourceLocation> TILE_TO_LOAD = new HashMap<>();
    public void preInit() throws IOException {
        System.out.println("pre init côté commun");
        GameRegistry.registerTileEntity(TileTombe.class, new ResourceLocation("westerlife", "tombe"));
        GameRegistry.registerTileEntity(TileMovingGate.class, new ResourceLocation("westerlife", "temovinggate"));
        GameRegistry.registerTileEntity(TEBisign.class, new ResourceLocation("westerlife", "tebisign"));
        GameRegistry.registerTileEntity(TETerminalDePaiement.class, new ResourceLocation("westerlife", "teterminaldepaiement"));
        GameRegistry.registerTileEntity(TileRadarFixe.class, new ResourceLocation("westerlife", "radarfixe"));
        GameRegistry.registerTileEntity(TileHerse.class, new ResourceLocation("westerlife", "herse"));
        GameRegistry.registerTileEntity(TileFeuRouge.class, new ResourceLocation("westerlife", "feurouge"));
        GameRegistry.registerTileEntity(TileSpot.class, new ResourceLocation("westerlife", "spot"));
        GameRegistry.registerTileEntity(TileLyre.class, new ResourceLocation("westerlife", "lyre"));
        GameRegistry.registerTileEntity(TileScreen.class, new ResourceLocation("westerlife", "screen"));
        GameRegistry.registerTileEntity(TilePoteauLevant.class, new ResourceLocation("westerlife", "tilepoteaulevant"));
        GameRegistry.registerTileEntity(TileChair.class, new ResourceLocation("westerlife", "chair"));
        GameRegistry.registerTileEntity(TileColoredBlock.class, new ResourceLocation("westerlife", "coloredblock"));
        GameRegistry.registerTileEntity(TileIrm.class, new ResourceLocation("westerlife", "irm"));
        GameRegistry.registerTileEntity(TilePorteNom.class, new ResourceLocation("westerlife", "portenom"));
        GameRegistry.registerTileEntity(TilePanneauAgglomeration.class, new ResourceLocation("westerlife", "panneauagglomeration"));
        GameRegistry.registerTileEntity(TilePanneauRue.class, new ResourceLocation("westerlife", "panneaurue"));
        GameRegistry.registerTileEntity(TilePanneauRueSP.class, new ResourceLocation("westerlife", "panneauruesp"));
        GameRegistry.registerTileEntity(TileAIPoint.class, new ResourceLocation("westerlife", "aipoint"));
        GameRegistry.registerTileEntity(TilePark.class, new ResourceLocation("westerlife", "park"));
        GameRegistry.registerTileEntity(TileGarage.class, new ResourceLocation("westerlife", "garage"));
        GameRegistry.registerTileEntity(TileMacdo.class, new ResourceLocation("westerlife", "macdo"));
        GameRegistry.registerTileEntity(TilePlayerSensor.class, new ResourceLocation("westerlife", "playersensor"));

        TILE_TO_LOAD.forEach(GameRegistry::registerTileEntity);

        SoundsHandler.registerSounds();
        MinecraftForge.EVENT_BUS.register(this);

//        ScreenshotEvent


//        GameRegistry.registerTileEntity(TEDigicode.class, new ResourceLocation("westerlife", "tedigicode"));
    }



    public static void discoverTiles(FMLConstructionEvent event) {
        Set<ASMDataTable.ASMData> modData = event.getASMHarvestedData().getAll(TileToRegister.class.getName());
        Iterator<ASMDataTable.ASMData> var2 = modData.iterator();

        while (true) {
            ASMDataTable.ASMData data;
            if (!var2.hasNext()) {
                return;
            }

            data = var2.next();
            String name = data.getClassName();

            try {
                Class<?> tileEntity = Class.forName(data.getClassName());
                if(tileEntity.getSuperclass().equals(TileEntitySyncClient.class)) {
                    System.out.println(tileEntity.getAnnotations());
                    TileToRegister tileToLoad = tileEntity.getAnnotation(TileToRegister.class);
                    System.out.println(tileEntity);
                    TILE_TO_LOAD.put((Class<? extends TileEntitySyncClient>) tileEntity, new ResourceLocation("westerlife", tileToLoad.location()));
                } else {
                    System.out.println("Tile " + name + " is not a TileEntitySyncClient");
                }




            } catch (Exception var12) {
                throw new RuntimeException("Failed to load tile class " + name, var12);
            }
        }
    }


    public void init() {

    }

    public void postInit() {

    }
}
