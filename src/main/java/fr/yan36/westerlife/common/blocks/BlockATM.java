package fr.yan36.westerlife.common.blocks;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.client.gui.CSSGuiGendarmerie;
import fr.yan36.westerlife.common.items.WesterItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import javax.swing.text.html.CSS;

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
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        System.out.println("atm");
        if(playerIn.getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.CB))) {
            ACsGuiApi.asyncLoadThenShowGui("atm", CSSGuiAtm::new);
        } else {
            playerIn.sendMessage(new TextComponentString("§cVous devez entrer votre carte bancaire."));
            System.out.println("atm2");
        }
        super.onBlockClicked(worldIn, pos, playerIn);
    }
}
