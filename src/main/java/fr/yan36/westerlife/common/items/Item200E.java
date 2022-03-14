package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Item200E extends Item {

    public static final String DEUXCENTEUROS = "deuxcenteuros";

    public Item200E() {
        super();

        WesterItem.setItemName(this, DEUXCENTEUROS);
        setCreativeTab(Main.creativeTab);
    }
}
