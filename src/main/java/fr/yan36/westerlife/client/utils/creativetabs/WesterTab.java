package fr.yan36.westerlife.client.utils.creativetabs;

import fr.yan36.westerlife.common.init.DynamXInit;
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
        } else if(Objects.equals(this.getTabLabel(), "westertab_economy")) {
            return new ItemStack(ItemInit.CARTE_BANCAIRE);
        } else if(Objects.equals(this.getTabLabel(), "westertab_roads")) {
            return new ItemStack(DynamXInit.feurouge);
        } else if(Objects.equals(this.getTabLabel(), "westertab_food")) {
            return new ItemStack(DynamXInit.WATER);
        } else {
            return new ItemStack(ItemInit.CINQ_EUROS);
        }
    }
}