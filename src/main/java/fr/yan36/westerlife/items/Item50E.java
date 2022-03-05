package fr.yan36.westerlife.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Item50E extends Item {

    public static final String CINQUANTEEUROS = "cinquanteeuros";

    public Item50E() {
        super();

        WesterItem.setItemName(this, CINQUANTEEUROS);
        setCreativeTab(Main.creativeTab);
    }
}
