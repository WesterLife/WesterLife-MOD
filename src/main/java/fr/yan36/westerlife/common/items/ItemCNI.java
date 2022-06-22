package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;

import net.minecraft.item.Item;

public class ItemCNI extends Item {

    public static final String CNI = "cni";

    public ItemCNI()
    {
        super();

        WesterItem.setItemName(this, CNI);
        setCreativeTab(Main.creativeTab);
    }
}
