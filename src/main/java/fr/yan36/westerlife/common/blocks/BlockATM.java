package fr.yan36.westerlife.common.blocks;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketOpenGUIAtm;
import fr.yan36.westerlife.common.network.PacketOpenGUIAtmServer;
import fr.yan36.westerlife.common.network.PacketReqSyncPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockATM extends Block {

    public static final String NAME = "atm";

    public BlockATM(Material material)
    {
        super(material);

        WesterBlocks.setBlockName(this, NAME);
        setResistance(5.0F);
        setHardness(3.0F);
        setCreativeTab(Main.creativeTab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            System.out.println("atm");
            if(playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.CB))) {
                Main.network.sendTo(new PacketOpenGUIAtmServer(), (EntityPlayerMP) playerIn);
            } else {
                playerIn.sendMessage(new TextComponentString("§cVous devez entrer votre carte bancaire."));
            }
        }
        return true;
    }
}
