package fr.yan36.westerlife.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCNI extends Item {

    public static final String CNI = "cni";

    public ItemCNI()
    {
        super();

        WesterItem.setItemName(this, CNI);
        setCreativeTab(CreativeTabs.MISC);
    }
}
