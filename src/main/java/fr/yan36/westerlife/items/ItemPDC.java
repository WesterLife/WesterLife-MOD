package fr.yan36.westerlife.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemPDC extends Item {

    public static final String PDC = "pdc";

    public ItemPDC() {
        super();

        WesterItem.setItemName(this, PDC);
        setCreativeTab(Main.creativeTab);
    }
}
