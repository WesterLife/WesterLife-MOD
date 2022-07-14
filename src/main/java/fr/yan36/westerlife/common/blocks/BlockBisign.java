package fr.yan36.westerlife.common.blocks;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.tileentity.TEBisign;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBisign extends DynamXBlock {

    public BlockBisign(Material material, String modid, String blockName, String model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.creativeTab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TEBisign te = (TEBisign) worldIn.getTileEntity(pos);
        switch (te.getTileData().getInteger("state")) {
            case 0:
                te.getTileData().setInteger("state", 1);
                break;
            case 1:
                te.getTileData().setInteger("state", 2);
                break;
            case 2:
                te.getTileData().setInteger("state", 0);
                break;
        }
        return true;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {

        return new TEBisign(this.blockObjectInfo);


    }
}
