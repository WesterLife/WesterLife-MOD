package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockComputer extends DynamXBlock {
    public BlockComputer(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }
}
