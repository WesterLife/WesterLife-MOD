package fr.gabidut76.westerlife.common.items;

import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCigarettePaper extends ItemBase {
    public ItemCigarettePaper(String name, int capacity)
    {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(capacity);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{Main.WESTER_MAIN};
    }



    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
