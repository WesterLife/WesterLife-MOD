package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class ItemFood extends net.minecraft.item.ItemFood implements IHasModel {
    public ItemFood(String name, int amount, float saturation)
    {
        super(amount, saturation, false);
        setRegistryName(name);
        setCreativeTab(Main.WESTER_FOOD);
        ItemInit.ITEMS.add(this);
        setMaxDamage(1);
        setTranslationKey(name);
    }


    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_FOOD;
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
