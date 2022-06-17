package fr.yan36.westerlife.common.blocks;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.tileentity.TEKeypad;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketOpenGUIKeypad;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockKeycard extends DynamXBlock {
    public BlockKeycard(Material material, String modid, String blockName, String model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.creativeTab);
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEKeypad(this.blockObjectInfo);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.COD))) {

            }
        }
        return true;
    }
}
