package fr.yan36.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileGarage;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.network.PacketOpenGuiWithObject;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.server.api.NemesisLink;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockGarage extends DynamXBlock {
    public BlockGarage(Material material, String modid, String blockName, ResourceLocation model) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_STAFF);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItem(hand).getItem().equals(DynamXInit.magicWand)) {
                BlockPos pos2 = Util.parseBlockPosFromString(playerIn.getHeldItem(hand).getTagCompound().getString("parkingLink"));
                TilePark te = (TilePark) worldIn.getTileEntity(pos2);


                TileGarage te2 = (TileGarage) worldIn.getTileEntity(pos);
                te2.setIsLinked(true);
                te2.setLinkedTo(pos2);

                if(te != null) {
                    te.setLinkedTo(pos);
                    playerIn.getHeldItem(hand).getTagCompound().removeTag("parkingLink");
                    playerIn.sendMessage(new TextComponentString("§aParking linked to garage."));
                } else {
                    playerIn.getHeldItem(hand).getTagCompound().removeTag("parkingLink");
                    playerIn.sendMessage(new TextComponentString("§4Error : §c the parking you are trying to link doesn't exists anymore."));
                }


            } else {
                TileGarage te = (TileGarage) worldIn.getTileEntity(pos);
                assert te != null;
                TilePark te2 = (TilePark) worldIn.getTileEntity(te.getLinkedTo());


                try {
                    String uid = NemesisLink.NEMESIS_API.getUserIdFromUUID(String.valueOf(playerIn.getUniqueID()));
                    List<GarageCar> cars = NemesisLink.NEMESIS_API.getGarageCars(uid);

                    Main.network.sendTo(new PacketOpenGuiWithObject(1, cars, Util.blockPosToString(te2.getPos()) ), (EntityPlayerMP) playerIn);
                } catch (IOException | NBTException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileGarage(blockObjectInfo);
    }
}
