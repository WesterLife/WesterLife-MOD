package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.item.Item;

public class Item20E extends Item {

    public static final String VINGTEUROS = "vingteuros";

    public Item20E() {
        super();

        WesterItem.setItemName(this, VINGTEUROS);
        setCreativeTab(Main.creativeTab);
    }
}
