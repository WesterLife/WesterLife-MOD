package fr.gabidut76.westerlife.common.init;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.armors.ArmorEpaulettes;
import fr.gabidut76.westerlife.common.armors.DynamXArmor;
import fr.gabidut76.westerlife.common.objects.GendInfos;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorInit {


    /**
     * @apiNote Resource location of the model to ignore when binding texture
     * The other is the texture to replace
     * */
    public static HashMap<ResourceLocation, ResourceLocation> IGNORE_BINDTEXTURE = new HashMap<>();
    public static List<DynamXArmor> ARMORS = new ArrayList<>();
    public static HashMap<String, ArmorEpaulettes> EPAULETTES = new HashMap<>();

    public static void init() {
//        register("tshirt", new ResourceLocation(Main.MODID, "models/dynamx/armors/tshirt/tshirt.obj"), EntityEquipmentSlot.CHEST);
//        for (GendInfos value : GendInfos.values()) {
//            System.out.println("Registering " + value.registryname);
//            ArmorEpaulettes ep = new ArmorEpaulettes(value.registryname, new ResourceLocation(Main.MODID, "models/dynamx/armors/epaulette/armors_epau.obj"), EntityEquipmentSlot.CHEST);
//            System.out.println(ep.getInfo().getVariants());
//            EPAULETTES.put(value.registryname, ep);
//            IGNORE_BINDTEXTURE.add(new ResourceLocation(Main.MODID, "models/dynamx/armors/tshirt/tshirt.obj"));
//        }
    }

    public static void register(String armorname, ResourceLocation modelloc, EntityEquipmentSlot... slots) {
        for (EntityEquipmentSlot slot : slots) {
            ARMORS.add(new DynamXArmor(armorname + "_" + slot.getName(), modelloc, slot));
        }
    }

}
