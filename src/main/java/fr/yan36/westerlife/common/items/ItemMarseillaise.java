package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ItemMarseillaise extends ItemRecord {

    public static final String MARSEILLAISE = "marseillaise";

    protected ItemMarseillaise(String recordName, SoundEvent soundIn) {
        super(recordName, soundIn);
        WesterItem.setItemName(this, MARSEILLAISE);
        setCreativeTab(Main.creativeTab);
    }
}
