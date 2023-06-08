package fr.yan36.westerlife.common.handlers;

import fr.yan36.westerlife.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {

    public static SoundEvent BIP;
    public static SoundEvent BIP2;
    public static SoundEvent MARSEILLAISE;
    public static SoundEvent ATMSOUNDBIP;

    public static void registerSounds() {
        BIP = registerSound("bip");
        BIP2 = registerSound("bip2");
        MARSEILLAISE = registerSound("marseillaise");
        ATMSOUNDBIP = registerSound("atmsoundbip");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Main.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
