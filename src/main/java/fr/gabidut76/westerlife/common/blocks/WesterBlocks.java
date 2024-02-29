package fr.gabidut76.westerlife.common.blocks;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.block.Block;

public class WesterBlocks {



    public static void setBlockName(Block block, String name)
    {
        block.setRegistryName(Main.MODID, name).setTranslationKey(Main.MODID + "." + name);
    }

}
