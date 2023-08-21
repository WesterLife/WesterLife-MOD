package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileColoredBlock;
import fr.yan36.westerlife.common.blocks.tileentity.TileIrm;
import fr.yan36.westerlife.common.network.PacketAnimationToAll;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockIRM extends DynamXBlock {

    public BlockIRM(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            TileIrm tile = (TileIrm) worldIn.getTileEntity(pos);
            assert tile != null;
            tile.setRunning(!tile.isRunning());
        } else {
            if (!worldIn.isRemote) {
                playerIn.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ() + 3f);
                Main.network.sendToAll(new PacketAnimationToAll(Animation.SLEEP.getId(), playerIn.getEntityId()));
                return true;
            }
        }

        return true;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileIrm(this.blockObjectInfo);
    }

}
