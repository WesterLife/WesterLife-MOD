package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockRoad extends DynamXBlock {

    public BlockRoad(Material material, String modid, String blockName, ResourceLocation model) {
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

    /*@Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

        return new Radar(this.blockObjectInfo);


    }*/
}
