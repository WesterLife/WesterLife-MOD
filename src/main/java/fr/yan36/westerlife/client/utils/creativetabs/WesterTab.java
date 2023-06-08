package fr.yan36.westerlife.client.utils.creativetabs;

import fr.yan36.westerlife.common.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class WesterTab extends CreativeTabs {

    public WesterTab(String label) {
        super(label);
    }


    @Override
    public ItemStack createIcon() {

        if(Objects.equals(this.getTabLabel(), "westertab_cards")) {
            return new ItemStack(ItemInit.CNI);
        } else {
            return new ItemStack(ItemInit.CINQ_EUROS);
        }
    }
}