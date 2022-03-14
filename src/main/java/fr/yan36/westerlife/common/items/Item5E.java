package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Item5E extends Item {

    public static final String CINQEUROS = "cinqeuros";

    public Item5E() {
        super();

        WesterItem.setItemName(this, CINQEUROS);
        setCreativeTab(Main.creativeTab);
    }


}
