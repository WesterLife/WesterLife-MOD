package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
//import fr.yan36.westerlife.common.blocks.tileentity.TEDigicode;
import fr.yan36.westerlife.common.network.PacketOpenMcefGui;
import fr.yan36.westerlife.common.network.old.PacketOpenGUIKeypad;
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
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public class BlockDigicode extends DynamXBlock {
    public BlockDigicode(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

//    @Nullable
//    public TileEntity createTileEntity(World world, IBlockState state) {
//        return new TEDigicode(this.blockObjectInfo);
//    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            Main.network.sendTo(new PacketOpenMcefGui("digicode", pos), (EntityPlayerMP) playerIn);
        }
        return true;
    }
}
