package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Item100E extends Item {

    public static final String CENTEUROS = "centeuros";

    public Item100E() {
        super();

        WesterItem.setItemName(this, CENTEUROS);
        setCreativeTab(Main.creativeTab);
    }
}
