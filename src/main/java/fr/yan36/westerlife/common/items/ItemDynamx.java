package fr.yan36.westerlife.common.items;

import fr.dynamx.common.contentpack.type.objects.AbstractItemObject;
import fr.dynamx.common.items.DynamXItem;
import fr.yan36.westerlife.Main;

public class ItemDynamx extends DynamXItem {


    public ItemDynamx(String modid, String itemName, String model) {
        super(modid, itemName, model);
        setCreativeTab(Main.creativeTab);
    }
}
