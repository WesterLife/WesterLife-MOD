package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
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
import java.util.Objects;

public class BlockBarriereLevante extends DynamXBlock {
    public BlockBarriereLevante(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_ROADS);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileMovingGate tile = (TileMovingGate) worldIn.getTileEntity(pos);
        if(tile != null && player.isCreative()) {
            tile.setState(Objects.equals(tile.getState(), "closed") ? "open" : "closed");
        }

        if(!worldIn.isRemote) {
            if(player.isCreative() && player.getHeldItem(hand).getItem().equals(DynamXInit.magicWand))
                Main.network.sendTo(new PacketOpenAcsGui(5, Util.blockPosToString(pos), "movinggate"), (EntityPlayerMP) player);
        }
        return true;
    }


    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMovingGate(this.blockObjectInfo);
    }
}
