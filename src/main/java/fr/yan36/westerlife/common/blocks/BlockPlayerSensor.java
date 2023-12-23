package fr.yan36.westerlife.common.blocks;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePlayerSensor;
import fr.yan36.westerlife.common.init.BlockInit;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class BlockPlayerSensor extends Block implements IHasModel {

    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockPlayerSensor(String name, Material material) {
        super(material);
        setRegistryName(name);
        setCreativeTab(Main.WESTER_MAIN);
        setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
        setBlockUnbreakable();

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));

    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if ((Boolean) state.getValue(POWERED))
        {
            i |= 8;
        }

        return i;

    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return super.getStrongPower(blockState, blockAccess, pos, side);
    }


    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
    }



    public void setPowered(World worldIn, BlockPos pos, IBlockState state, int delay) {
        worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.TRUE), 2);
        worldIn.markBlockRangeForRenderUpdate(pos, pos);
        worldIn.scheduleUpdate(new BlockPos(pos), this, delay);
        worldIn.notifyNeighborsOfStateChange(pos, this, true);
    }


    private void notifyNeighbors(World worldIn, BlockPos pos)
    {


        TilePlayerSensor te = (TilePlayerSensor) worldIn.getTileEntity(pos);
        assert te != null;
        int radius = te.getRadius();

        for (int x = -(radius); x <= radius; x ++)
        {
            for (int y = -(radius); y <= radius; y ++)
            {
                for (int z = -(radius); z <= radius; z ++)
                {
                    BlockPos pos2 = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    worldIn.notifyNeighborsOfStateChange(pos2, this, true);
                    worldIn.markBlockRangeForRenderUpdate(pos2, pos2);
                    worldIn.scheduleUpdate(pos2, this, this.tickRate(worldIn));
                }
            }
        }

    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(POWERED)).booleanValue())
            {
                this.checkPressed(state, worldIn, pos);

            }
        }
    }
    private void checkPressed(IBlockState state, World worldIn, BlockPos pos)
    {
        boolean flag1 = ((Boolean)state.getValue(POWERED)).booleanValue();


        if (flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)));
            worldIn.notifyNeighborsOfStateChange(pos, this, true);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }

        if (flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)));
            worldIn.notifyNeighborsOfStateChange(pos, this, true);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {POWERED});
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePlayerSensor();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(player.isCreative() && player.getHeldItem(hand).getItem().equals(DynamXInit.magicWand))
                Main.network.sendTo(new PacketOpenAcsGui(5, Util.blockPosToString(pos), "sensor"), (EntityPlayerMP) player);

        }
        return true;
    }
}
