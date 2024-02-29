package fr.gabidut76.westerlife.common.items.dynamx;

import fr.dynamx.common.items.DynamXItem;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.*;

public class ItemMagicWand extends DynamXItem {


    public ItemMagicWand(String modid, String itemName, ResourceLocation model) {
        super(modid, itemName, model);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{Main.WESTER_STAFF};
    }

}
