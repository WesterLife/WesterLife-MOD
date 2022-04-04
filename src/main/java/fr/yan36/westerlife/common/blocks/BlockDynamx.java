package fr.yan36.westerlife.common.blocks;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import net.minecraft.block.material.Material;

public class BlockDynamx extends DynamXBlock {

    public BlockDynamx(Material material, String modid, String blockName, String model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.creativeTab);
    }

    /*@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Radar te = (Radar) worldIn.getTileEntity(pos);
        te.getOutputSignal();
        te.setOutputSignal(1);
        return true;
    }
     */

    /*@Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

        return new Radar(this.blockObjectInfo);


    }*/
}
