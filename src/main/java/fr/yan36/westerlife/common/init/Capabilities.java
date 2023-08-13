package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatCapability;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatProvider;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStat;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Capabilities {
    @SubscribeEvent
    public static void registerCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if(e.getObject() instanceof EntityPlayer) {
            e.addCapability(new ResourceLocation(Main.MODID, "moneycapa"), new PlayerStatProvider(new PlayerStat(Animation.NONE)));
            Main.logger.info("Capability added to player.");
        }
    }

    public static void init() {
        Main.logger.info("Capabilities initialized");
        CapabilityManager.INSTANCE.register(IPlayerStat.class, new PlayerStatCapability.Storage(), () -> new PlayerStat(Animation.NONE));
    }
}
