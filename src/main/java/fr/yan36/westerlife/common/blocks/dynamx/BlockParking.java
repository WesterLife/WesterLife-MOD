package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileGarage;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.items.dynamx.ItemMagicWand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockParking extends DynamXBlock {
    public BlockParking(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_STAFF);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItem(hand).getItem().equals(DynamXInit.magicWand)) {

                TilePark te = (TilePark) worldIn.getTileEntity(pos);
                if(!(te.getLinkedTo().equals(new BlockPos(-1,-1,-1)) || te.getLinkedTo().equals(new BlockPos(-1,-1,-1)))) {
                    TileGarage te2 = (TileGarage) worldIn.getTileEntity(te.getLinkedTo());
                    te2.setIsLinked(false);
                    te.setLinkedTo(new BlockPos(-1,-1,-1));
                }

                ItemStack item = playerIn.getHeldItem(hand);

                if(!item.hasTagCompound()) {
                    item.setTagCompound(new NBTTagCompound());

                }

                assert item.getTagCompound() != null;
                item.getTagCompound().setString("parkingLink", Util.blockPosToString(pos));



            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePark(blockObjectInfo);
    }
}
