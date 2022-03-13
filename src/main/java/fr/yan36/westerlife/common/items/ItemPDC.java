package fr.yan36.westerlife.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemPDC extends Item {

    public static final String PDC = "pdc";

    public ItemPDC() {
        super();

        WesterItem.setItemName(this, PDC);
        setCreativeTab(CreativeTabs.MISC);
    }
}
