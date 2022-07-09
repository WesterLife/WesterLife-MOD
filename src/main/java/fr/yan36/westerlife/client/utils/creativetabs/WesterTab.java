package fr.yan36.westerlife.client.utils.creativetabs;

import fr.yan36.westerlife.common.items.WesterItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class WesterTab extends CreativeTabs {

    public WesterTab(String label) {
        super(label);
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack(WesterItem.CB);
    }
}