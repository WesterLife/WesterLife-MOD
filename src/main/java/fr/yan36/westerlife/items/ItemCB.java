package fr.yan36.westerlife.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCB extends Item {

    public static final String CB = "cb";

    public ItemCB() {
        super();

        WesterItem.setItemName(this, CB);
        setCreativeTab(Main.creativeTab);
    }
}
