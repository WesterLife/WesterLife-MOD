package fr.yan36.westerlife.creativetabs;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.items.WesterItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class WesterTab extends CreativeTabs {

    public WesterTab() {
        super(Main.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(WesterItem.CNI);
    }
}
