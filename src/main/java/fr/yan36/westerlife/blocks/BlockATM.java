package fr.yan36.westerlife.blocks;

import fr.yan36.westerlife.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockATM extends Block {

    public static final String NAME = "atm";

    public BlockATM(Material material)
    {
        super(material);

        WesterBlocks.setBlockName(this, NAME);
        setResistance(5.0F);
        setHardness(3.0F);
        setCreativeTab(Main.creativeTab);
    }
}
