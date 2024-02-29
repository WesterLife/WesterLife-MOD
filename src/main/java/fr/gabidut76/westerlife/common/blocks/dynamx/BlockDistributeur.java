package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDistributeur extends DynamXBlock {
    public BlockDistributeur(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_MAIN);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!(worldIn.isRemote)) {
            if(playerIn.getHeldItem(hand).hasTagCompound()) {
                Main.network.sendTo(new PacketOpenAcsGui(10, playerIn.getHeldItem(hand).getTagCompound().getString("relatedBankAccount"), "no"), (EntityPlayerMP) playerIn);
            } else {
                Main.network.sendTo(new PacketOpenAcsGui(10, "null", "yes"), (EntityPlayerMP) playerIn);

            }

        }
        return true;
    }
}
