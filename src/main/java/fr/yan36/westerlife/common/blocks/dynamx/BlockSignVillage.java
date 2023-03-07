package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TESign;
import fr.yan36.westerlife.common.network.PacketOpenGUIEditSign;
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

public class BlockSignVillage extends DynamXBlock {

    public BlockSignVillage(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_TAB);
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TESign(this.blockObjectInfo);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.isCreative()) {
                System.out.println("open gui");
                Main.network.sendTo(new PacketOpenGUIEditSign(Util.blockPosToString(pos)), (EntityPlayerMP) playerIn);
            }
        }
        return true;
    }


    /*@Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new Radar(this.blockObjectInfo);
    }*/
}
