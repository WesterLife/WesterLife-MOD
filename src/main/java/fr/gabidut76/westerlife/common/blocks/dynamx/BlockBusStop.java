package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.betterlights.BetterLightsMod;
import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileBusStop;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileTombe;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import fr.gabidut76.westerlife.westercore.Main;
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

public class BlockBusStop extends DynamXBlock {

    public BlockBusStop(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos2, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(player.isCreative() && player.getHeldItem(hand).getItem().equals(DynamXInit.magicWand))
                Main.network.sendTo(new PacketOpenAcsGui(5, Util.blockPosToString(pos2), "busstop"), (EntityPlayerMP) player);

        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        TileBusStop tile = (TileBusStop) worldIn.getTileEntity(pos);
        if(tile != null) {
            tile.lightCasters.forEach(lightCaster -> BetterLightsMod.getLightManager().removeLightCaster(lightCaster, true));

        }
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileBusStop(this.blockObjectInfo);
    }

}
