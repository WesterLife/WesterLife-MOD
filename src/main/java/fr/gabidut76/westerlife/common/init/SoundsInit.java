package fr.gabidut76.westerlife.common.init;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsInit {

    public static SoundEvent BIP;
    public static SoundEvent BIP2;
    public static SoundEvent MARSEILLAISE;
    public static SoundEvent ATMSOUNDBIP;
    public static SoundEvent IRM_RUNNING;
    public static SoundEvent IRM_ALARM;
    public static SoundEvent CARALARM;
    public static SoundEvent COKECUT;

    public static void registerSounds() {
        BIP = registerSound("bip");
        BIP2 = registerSound("bip2");
        MARSEILLAISE = registerSound("marseillaise");
        ATMSOUNDBIP = registerSound("atmsoundbip");
        IRM_RUNNING = registerSound("irm");
        IRM_ALARM = registerSound("alarmbip");
        CARALARM = registerSound("caralarm");
        COKECUT = registerSound("cokecut");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Main.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
