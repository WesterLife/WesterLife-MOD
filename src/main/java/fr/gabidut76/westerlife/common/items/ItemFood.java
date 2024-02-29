package fr.gabidut76.westerlife.common.items;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;

import javax.annotation.Nullable;

public class ItemFood extends net.minecraft.item.ItemFood implements IHasModel {
    public ItemFood(String name, int amount, float saturation)
    {
        super(amount, saturation, false);
        setRegistryName(Main.MODID, name);
        ItemInit.ITEMS.add(this);
        setTranslationKey(name);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{Main.WESTER_FOOD};
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
