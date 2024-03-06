package fr.gabidut76.westerlife.common.armors;

import fr.dynamx.common.contentpack.type.MaterialVariantsInfo;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.objects.GendInfos;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;

public class ArmorEpaulettes extends DynamXItemArmor {



    public ArmorEpaulettes(String armorname, ResourceLocation modelloc, EntityEquipmentSlot slot) {
        super(Main.MODID, armorname, modelloc, ArmorMaterial.LEATHER, slot);
//        getInfo().setArmorArms(new String[]{"leftArmModel", "rightArmModel"});

//        MaterialVariantsInfo m = new MaterialVariantsInfo<>(this.getInfo());
//        m.setTexturesArray(Arrays.stream(GendInfos.values()).map(gendInfos -> gendInfos.modelLocation).toArray(String[]::new));
//
//        m.appendTo(this.getInfo());

//        MaterialVariantsInfo a = new MaterialVariantsInfo(this.getInfo());
//        a.setTexturesArray(Arrays.stream(GendInfos.values()).map(gendInfos -> gendInfos.modelLocation).toArray(String[]::new));

        setCreativeTab(Main.WESTER_GEND);
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
//        this.armorInfo.getObjArmor().
        return this.armorInfo.getObjArmor();
    }
}
