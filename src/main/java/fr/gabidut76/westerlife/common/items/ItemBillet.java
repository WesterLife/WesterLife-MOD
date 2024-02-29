package fr.gabidut76.westerlife.common.items;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class ItemBillet extends Item implements IHasModel {
    public ItemBillet(String name)
    {
        ItemInit.ITEMS.add(this);
        setRegistryName(Main.MODID, name);
//        setMaxDamage(1);
        setTranslationKey(name);
    }


    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_ECO;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
