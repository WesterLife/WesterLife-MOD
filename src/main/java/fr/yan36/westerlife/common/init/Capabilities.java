package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.playerchunckrel.IPlayerChunk;
import fr.yan36.westerlife.common.capabilities.playerchunckrel.PlayerChunkRel;
import fr.yan36.westerlife.common.capabilities.playerchunckrel.PlayerChunkRelCapability;
import fr.yan36.westerlife.common.capabilities.playerchunckrel.PlayerChunkRelProvider;
import fr.yan36.westerlife.common.capabilities.playergarage.IPlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageProvider;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemContainer;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemProvider;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatCapability;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatProvider;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStat;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import java.util.UUID;

import static fr.yan36.westerlife.common.handlers.RegistryHandler.CAPABILITY_LOCATION;

@Mod.EventBusSubscriber
public class Capabilities {
    public static final Character DEFAULT_CHARACTER = new Character(UUID.fromString("920206b1-35ac-48dd-8685-e47040d8bab8"), "M. Nobody", "Card errored", "null", Character.Gender.MALE, "null","null", new Permis());
    @SubscribeEvent
    public static void registerCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if(e.getObject() instanceof EntityPlayer) {
            e.addCapability(new ResourceLocation(Main.MODID, "moneycapa"), new PlayerStatProvider(new PlayerStat(Animation.NONE, DEFAULT_CHARACTER)));
            e.addCapability(new ResourceLocation(Main.MODID, "extraslots"), new ExtraItemProvider(new ExtraItemContainer((EntityPlayer) e.getObject())));
            e.addCapability(new ResourceLocation(Main.MODID, "garage"), new PlayerGarageProvider(new PlayerGarage()));


            Main.logger.info("Capability added to player.");
        }
    }


    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent <Chunk> event) {
        event.addCapability(new ResourceLocation("modid", "pollution"), new PlayerChunkRelProvider(new PlayerChunkRel()));
    }

    public static void init() {
        Main.logger.info("Capabilities initialized");
        CapabilityManager.INSTANCE.register(IPlayerStat.class, new PlayerStatCapability.Storage(), () -> new PlayerStat(Animation.NONE, DEFAULT_CHARACTER));
        CapabilityManager.INSTANCE.register(IPlayerGarage.class, new PlayerGarageCapability.Storage(), PlayerGarage::new);
        CapabilityManager.INSTANCE.register(IExtraItemHandler.class, new ExtraItemCapability.Storage(), ExtraItemContainer::new);
        CapabilityManager.INSTANCE.register(IPlayerChunk.class, new PlayerChunkRelCapability.Storage(), PlayerChunkRel::new);
        MinecraftForge.EVENT_BUS.register(new PlayerStatCapability());
        MinecraftForge.EVENT_BUS.register(new PlayerGarageCapability());
        MinecraftForge.EVENT_BUS.register(new ExtraItemCapability());
    }
}
