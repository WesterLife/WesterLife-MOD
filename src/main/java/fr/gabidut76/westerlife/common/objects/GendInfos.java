package fr.gabidut76.westerlife.common.objects;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.util.ResourceLocation;

public enum GendInfos {
    PREMIERECLASS("Gendarme de 1ère classe", "premiereclasse", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/premiereclasse.png"), false),
    BRIGADIER("Brigadier", "brigadier", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/brigadier.png"), false),
    BRIGADIERCHEF("Brigadier-Chef", "brigadierchef", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/brigadierchef.png"), false),
    GENDARME("Gendarme", "gendarme", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/gendarme.png"), false),
    GENDARMECAR("Gendarme de Carrière", "gendarmecar", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/gendarmecar.png"), false),
    MARECHALDESLOGIS("Maréchal des Logis", "marechal", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/marechal.png"), false),
    ADJUDANT("Adjudant", "adjudant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/adjudant.png"), false),
    ADJUDANTCHEF("Adjudant-Chef", "adjudantchef", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/adjudantchef.png"), false),
    MAJOR("Major", "major", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/major.png"), false),
    ELEVEOFFICIER("Elève-Officier", "eleveofficier", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/eleveofficier.png"), false),
    SOUSLIEUTENANT("Sous-Lieutenant", "souslieutenant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/souslieutenant.png"), false),
    LIEUTENANT("Lieutenant", "lieutenant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/lieutenant.png"), false),
    CAPITAINE("Capitaine", "capitaine", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/capitaine.png"), false),
    CHEFESCADRON("Chef d'Escadron", "chefescadron", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/chefescadron.png"), false),
    LIEUTENANTCOLONEL("Lieutenant-Colonel", "lieutenantcolonel", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/lieutenantcolonel.png"), false),
    COLONEL("Colonel", "colonel", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/colonel.png"), false),
    GENBRIG("Général de Brigade", "genbrig", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/genbrig.png"), false),
    GENDIV("Général de Division", "gendiv", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/gendiv.png"), false),
    GENCORPSARMEE("Général de Corps d'Armée", "gencorpsarmee", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/gencorpsarmee.png"), false),
    GENERALARMEE("Général d'Armée", "generalarmee", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/generalarmee.png"), false),

    MOB_MARECHALLOGIS("Maréchal des Logis", "mob_marechal", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/marechal.png"), true),
    MOB_GENDARME("Gendarme", "mob_gendarme", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/gendarme.png"), true),
    MOB_MARECHALLOGISCHEF("Maréchal des Logis-Chef", "mob_marechalchef", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/marechalchef.png"), true),
    MOB_ADJUDANT("Adjudant", "mob_adjudant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/adjudant.png"), true),
    MOB_ADJUDANTCHEF("Adjudant-Chef", "mob_adjudantchef", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/adjudantchef.png"), true),
    MOB_MAJOR("Major", "mob_major", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/major.png"), true),
    MOB_ELEVEOFFICIER("Elève-Officier", "mob_eleveofficier", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/eleveoff.png"), true),
    MOB_SOUSLIEUTENANT("Sous-Lieutenant", "mob_souslieutenant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/souslieutenant.png"), true),
    MOB_LIEUTENANT("Lieutenant", "mob_lieutenant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/lieutenant.png"), true),
    MOB_CAPITAINE("Capitaine", "mob_capitaine", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/capitaine.png"), true),
    MOB_COMMANDANT("Commandant", "mob_commandant", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/commandant.png"), true),
    MOB_LIEUTENANTCOLONEL("Lieutenant-Colonel", "mob_lieutenantcolonel", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/lieutenantcolonel.png"), true),
    MOB_COLONEL("Colonel", "mob_colonel", new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/textures/mobile/colonel.png"), true);

    public String displayname;
    public String registryname;
    public ResourceLocation modelLocation;
    public boolean isMobile;

    GendInfos(String displayname, String registryname, ResourceLocation textureLocation, boolean isMobile) {
        this.displayname = displayname;
        this.registryname = registryname;
        this.modelLocation = textureLocation;
        this.isMobile = isMobile;
    }
}
