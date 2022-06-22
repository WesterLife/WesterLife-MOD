package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.item.Item;

public class Item10E extends Item {

    public static final String DIXEUROS = "dixeuros";

    public Item10E() {
        super();

        WesterItem.setItemName(this, DIXEUROS);
        setCreativeTab(Main.creativeTab);
    }


}
