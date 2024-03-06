package fr.gabidut76.westerlife.common.armors;

import fr.dynamx.common.items.DynamXItemArmor;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class DynamXArmor extends DynamXItemArmor {

    public DynamXArmor(String armorname, ResourceLocation modelloc, EntityEquipmentSlot slot) {
        super(Main.MODID, armorname, modelloc, ArmorMaterial.LEATHER, slot);

//        getInfo().setArmorArms(new String[]{"armLeft", "armRight"});
//        getInfo().setArmorBody("body");
//        getInfo().setArmorHead("head");
//        getInfo().setArmorLegs(new String[]{"legLeft", "legRight"});
//        getInfo().setArmorFoot(new String[]{"footLeft", "footRight"});

    }

}
