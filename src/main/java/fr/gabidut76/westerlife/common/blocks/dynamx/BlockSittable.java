package fr.gabidut76.westerlife.common.blocks.dynamx;

import com.jme3.math.Vector3f;
import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockSittable extends DynamXBlock {

    public Vector3f seatModifier;

    public BlockSittable(Material material, String modid, String blockName, ResourceLocation model, Vector3f seatModifier) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
        this.seatModifier = seatModifier;
    }

    public Vector3f getSeatModifier() {
        return seatModifier;
    }
}
