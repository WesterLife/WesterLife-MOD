package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileChair;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
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

public class BlockChair extends BlockDynamx {

    public BlockChair(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileChair(blockObjectInfo);
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.isCreative() && playerIn.getHeldItem(hand).getItem().equals(DynamXInit.magicWand))
                Main.network.sendTo(new PacketOpenAcsGui(2, Util.blockPosToString(pos), ""), (EntityPlayerMP) playerIn);
        }
        return false;
    }
}
