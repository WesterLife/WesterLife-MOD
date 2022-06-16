package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.item.Item;

public class ItemCOD extends Item {

    public static final String COD = "cod";

    public ItemCOD() {
        super();

        WesterItem.setItemName(this, COD);
        setCreativeTab(Main.creativeTab);
    }
}
