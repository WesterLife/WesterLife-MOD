package fr.gabidut76.westerlife.common.items;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name)
    {
        setRegistryName(Main.MODID, name);
        setTranslationKey(name);
        ItemInit.ITEMS.add(this);
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
