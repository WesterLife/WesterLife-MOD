package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;

public class BlockMacdo extends DynamXBlock {
    public BlockMacdo(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            TileMacdo tile = (TileMacdo) worldIn.getTileEntity(pos);

            assert tile != null;
            tile.setSteakstate(tile.getSteakstate() + 1);


            if(playerIn.isSneaking()) {
                tile.setSteakstate(0);
                tile.setBurgeringredients(Collections.emptyList());
            } else {
                if(tile.getBurgeringredients().isEmpty()) {
                    Main.network.sendTo(new PacketOpenAcsGui(7, "base", Util.blockPosToString(pos)), (EntityPlayerMP) playerIn);
                } else if(tile.getBurgeringredients().get(0).equals(TileMacdo.burger.BREAD)) {
                    Main.network.sendTo(new PacketOpenAcsGui(7, "burger", Util.blockPosToString(pos)), (EntityPlayerMP) playerIn);
                } else if(tile.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                    Main.network.sendTo(new PacketOpenAcsGui(7, "baguette", Util.blockPosToString(pos)), (EntityPlayerMP) playerIn);
                } else {
                    Main.network.sendTo(new PacketOpenAcsGui(7, "base", Util.blockPosToString(pos)), (EntityPlayerMP) playerIn);
                }
            }

            /*

            else {
                if(tile.getSteakstate() == 1) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.BREAD)));
                }
                if(tile.getSteakstate() == 2) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.SALAD)));
                }
                if(tile.getSteakstate() == 3) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.TOMATO)));
                }
                if(tile.getSteakstate() == 4) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.STEAK)));
                }
                if(tile.getSteakstate() == 5) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.CHEESE)));
                }
                if(tile.getSteakstate() == 6) {
                    tile.addBurgeringredient(new ArrayList<>(Collections.singletonList(TileMacdo.burger.BREAD)));
                }
            }
            */
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMacdo(blockObjectInfo);
    }
}
