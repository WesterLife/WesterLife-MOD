package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.tileentity.TileCarPresentation;
import fr.yan36.westerlife.common.blocks.tileentity.TileTV;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockCarPresentation extends DynamXBlock {

    public BlockCarPresentation(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

        return new TileCarPresentation(this.blockObjectInfo);


    }
}
