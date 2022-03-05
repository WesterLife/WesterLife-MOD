package fr.yan36.westerlife.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Item500E extends Item {

    public static final String CINQCENTEUROS = "cinqcenteuros";

    public Item500E() {
        super();

        WesterItem.setItemName(this, CINQCENTEUROS);
        setCreativeTab(Main.creativeTab);
    }
}
