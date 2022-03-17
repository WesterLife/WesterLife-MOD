package fr.yan36.westerlife.common.blocks;

import fr.yan36.westerlife.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class WesterBlocks {

    public static final Block ATM = new BlockATM(Material.ROCK);

    public static void setBlockName(Block block, String name)
    {
        block.setRegistryName(Main.MODID, name).setTranslationKey(Main.MODID + "." + name);
    }

}
