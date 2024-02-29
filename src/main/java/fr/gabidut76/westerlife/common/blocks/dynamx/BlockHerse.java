package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileHerse;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockHerse extends DynamXBlock {

    public BlockHerse(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_ROADS);
    }

    /*@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Radar te = (Radar) worldIn.getTileEntity(pos);
        te.getOutputSignal();
        te.setOutputSignal(1);
        return true;
    }
     */

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

        return new TileHerse(this.blockObjectInfo);


    }
}
