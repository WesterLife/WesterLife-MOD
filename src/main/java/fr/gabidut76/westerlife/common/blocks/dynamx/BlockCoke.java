package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCoke;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.init.SoundsInit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCoke extends DynamXBlock {


    public BlockCoke(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_ILLEGAL);

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            TileCoke tile = (TileCoke) worldIn.getTileEntity(pos);
            if (tile != null) {
                if(worldIn.getTotalWorldTime() - tile.placedAt > (20 * 60 * 6) && playerIn.getHeldItem(hand).getItem() == DynamXInit.shears) {
                    playerIn.playSound(SoundsInit.COKECUT, 1.0F, 1.0F);
                    tile.placedAt = worldIn.getTotalWorldTime();
                    tile.sync();
                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.coke, Util.randBetween(1,4)));
                    worldIn.spawnEntity(item);
                }
                if(playerIn.getHeldItem(hand).getItem() == DynamXInit.magicWand) {
                    tile.placedAt = worldIn.getTotalWorldTime() - (20 * 60 * 6);
                    tile.sync();
                }
                if(playerIn.getHeldItem(hand).getItem() == ItemInit.CNI) {
                    playerIn.sendMessage(new TextComponentString("Placed at: " + (worldIn.getTotalWorldTime() - tile.placedAt) + " ticks"));
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCoke(blockObjectInfo);
    }
}
