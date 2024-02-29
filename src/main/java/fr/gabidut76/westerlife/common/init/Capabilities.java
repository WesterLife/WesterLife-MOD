package fr.gabidut76.westerlife.common.init;

import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.IPlayerChunk;
import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.PlayerChunkRel;
import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.PlayerChunkRelCapability;
import fr.gabidut76.westerlife.common.capabilities.playerchunckrel.PlayerChunkRelProvider;
import fr.gabidut76.westerlife.common.capabilities.playergarage.IPlayerGarage;
import fr.gabidut76.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.gabidut76.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.gabidut76.westerlife.common.capabilities.playergarage.PlayerGarageProvider;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemContainer;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemProvider;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.gabidut76.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Capabilities {



    @SubscribeEvent
    public static void registerCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if(e.getObject() instanceof EntityPlayer) {
            System.out.println("Attaching all capabilities to player.");

            e.addCapability(new ResourceLocation(Main.MODID, "playerstat"), new PlayerStatData.PlayerStatProvider());
            e.addCapability(new ResourceLocation(Main.MODID, "extraslots"), new ExtraItemProvider(new ExtraItemContainer((EntityPlayer) e.getObject())));
            e.addCapability(new ResourceLocation(Main.MODID, "garage"), new PlayerGarageProvider(new PlayerGarage()));


            Main.logger.info("Attaching all capabilities to player.");
        }
    }


    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent <Chunk> event) {
        event.addCapability(new ResourceLocation("modid", "pollution"), new PlayerChunkRelProvider(new PlayerChunkRel()));
    }

    public static void init() {
        Main.logger.info("Capabilities initialized");
        CapabilityManager.INSTANCE.register(IPlayerStat.class, new PlayerStatData.Storage(), PlayerStatData::new);
        CapabilityManager.INSTANCE.register(IPlayerGarage.class, new PlayerGarageCapability.Storage(), PlayerGarage::new);
        CapabilityManager.INSTANCE.register(IExtraItemHandler.class, new ExtraItemCapability.Storage(), ExtraItemContainer::new);
        CapabilityManager.INSTANCE.register(IPlayerChunk.class, new PlayerChunkRelCapability.Storage(), PlayerChunkRel::new);
        MinecraftForge.EVENT_BUS.register(new PlayerStatHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerGarageCapability());
        MinecraftForge.EVENT_BUS.register(new ExtraItemCapability());
    }
}
