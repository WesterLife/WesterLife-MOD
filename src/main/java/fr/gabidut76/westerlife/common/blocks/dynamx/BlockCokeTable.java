package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCoke;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCokeTable;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.init.SoundsInit;
import fr.gabidut76.westerlife.common.items.ItemCigarettePaper;
import fr.gabidut76.westerlife.westercore.Main;
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

public class BlockCokeTable extends DynamXBlock {


    public BlockCokeTable(Material material, String modid, String blockName, ResourceLocation model) {
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
            TileCokeTable tile = (TileCokeTable) worldIn.getTileEntity(pos);
            if(tile != null) {
                if(playerIn.getHeldItem(hand).getItem().equals(ItemInit.coke) && tile.placedLeavesInPot <= 4 && !tile.isResinReady) {
                    playerIn.getHeldItem(hand).shrink(1);
                    tile.placedLeavesInPot++;
                    tile.sync();
                }
                if(playerIn.getHeldItem(hand).getItem().equals(DynamXInit.cokeconcasseur) && tile.placedLeavesInPot == 5) {
                    tile.placedLeavesInPot = 6;
                    tile.placedAt = worldIn.getTotalWorldTime();
                    tile.sync();
                }
                if(tile.placedLeavesInPot == 7) {
                    tile.placedLeavesInPot = 1;
                    tile.isResinReady = false;
                    tile.placedAt = 0;
                    tile.sync();
                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.canabis_resine, 1));
                    worldIn.spawnEntity(item);
                }
                if(playerIn.getHeldItem(hand).getItem().equals(ItemInit.canabis_resine) && !tile.isResinReady && tile.placedLeavesInPot == 1) {
                    playerIn.getHeldItem(hand).shrink(1);
                    tile.isResinReady = true;
                    tile.sync();
                }
                System.out.println(playerIn.getHeldItem(hand).getItemDamage());
                if(playerIn.getHeldItem(hand).getItem().equals(ItemInit.cigarette_paper) && tile.isResinReady && tile.placedLeavesInPot == 1 && playerIn.getHeldItem(hand).getItemDamage() < 32) {
                    ItemCigarettePaper paper = (ItemCigarettePaper) playerIn.getHeldItem(hand).getItem();
                    paper.setDamage(playerIn.getHeldItem(hand), paper.getDamage(playerIn.getHeldItem(hand)) + 1);
                    tile.isResinReady = false;


                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.joint, 1));
                    worldIn.spawnEntity(item);
                    tile.sync();
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCokeTable(blockObjectInfo);
    }
}
