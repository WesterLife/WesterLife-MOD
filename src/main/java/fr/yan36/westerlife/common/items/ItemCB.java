package fr.yan36.westerlife.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCB extends Item {

    public static final String CB = "cb";

    public ItemCB() {
        super();

        WesterItem.setItemName(this, CB);
        setCreativeTab(CreativeTabs.MISC);
    }
}
