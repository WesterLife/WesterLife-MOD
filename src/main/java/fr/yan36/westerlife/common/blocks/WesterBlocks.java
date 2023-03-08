package fr.yan36.westerlife.common.blocks;

import fr.yan36.westerlife.Main;
import net.minecraft.block.Block;

public class WesterBlocks {



    public static void setBlockName(Block block, String name)
    {
        block.setRegistryName(Main.MODID, name).setTranslationKey(Main.MODID + "." + name);
    }

}
