package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauAgglomeration;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauRue;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.objects.IObjectEditable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPanneauRue extends DynamXBlock {

    public static enum Type {
        WALL,
        PILLAR
    }
    public Type type;
    public BlockPanneauRue(Material material, String modid, String blockName, ResourceLocation model, Type type) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
        this.type = type;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePanneauRue(this.blockObjectInfo);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos2, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(player.isCreative() && player.getHeldItem(hand).getItem().equals(DynamXInit.magicWand))
                Main.network.sendTo(new PacketOpenAcsGui(5, Util.blockPosToString(pos2), "prue"), (EntityPlayerMP) player);

        }
        return true;
    }

    public Type getType() {
        return type;
    }

    /*@Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new Radar(this.blockObjectInfo);
    }*/
}
