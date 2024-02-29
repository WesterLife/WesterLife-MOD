package fr.gabidut76.westerlife.common.blocks.dynamx;

import fr.dynamx.common.blocks.DynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileAIPoint;
import fr.gabidut76.westerlife.common.items.ItemCard;
import fr.gabidut76.westerlife.common.items.dynamx.ItemMagicWand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockAIPoint extends DynamXBlock {
    public enum Type {
        GO(new ResourceLocation("dynamxmod","textures/icons/gopoint.png")),
        STOP(new ResourceLocation("dynamxmod","textures/icons/stoppoint.png")),
        TURN(new ResourceLocation("dynamxmod","textures/icons/rotapoint.png")),
        DOMAC_SPAWN(new ResourceLocation("dynamxmod","textures/icons/domacspawn.png")),
        DOMAC_TARGET(new ResourceLocation("dynamxmod","textures/icons/domactarget.png"));

        final ResourceLocation resourceLocation;
        Type(ResourceLocation loaction) {
            this.resourceLocation = loaction;
        }

        public ResourceLocation getResourceLocation() {
            return resourceLocation;
        }
    }

    private final Type type;

    public BlockAIPoint(Material material, String modid, String blockName, ResourceLocation model, Type type) {
        super(material, modid, blockName, model);
        setCreativeTab(Main.WESTER_STAFF);
        this.type = type;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }



    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(playerIn.getHeldItemMainhand().getItem() instanceof ItemCard && playerIn.canUseCommand(4, "/op")) {
                if(worldIn.getTileEntity(pos) instanceof TileAIPoint) {
                    TileAIPoint te = (TileAIPoint) worldIn.getTileEntity(pos);
                    if(playerIn.isSneaking()) {

                    } else {
                        System.out.println(te.getRelatedEntity());
                        if(te.getRelatedEntity() != null) {
                            te.getRelatedEntity().setDead();
                            te.getRelatedEntity().setPosition(pos.getX(), pos.getY() + 5, pos.getZ());
                            te.setRelatedEntity(null);
                        }

                        playerIn.sendMessage(new net.minecraft.util.text.TextComponentString(te.getTarget()));
                    }
                }
            }
            if(playerIn.getHeldItemMainhand().getItem() instanceof ItemMagicWand && playerIn.canUseCommand(4, "/op")) {
                if(worldIn.getTileEntity(pos) instanceof TileAIPoint) {
                    ItemStack stack = playerIn.getHeldItemMainhand();
                    if(this.type == Type.DOMAC_SPAWN) {
                        if(!stack.hasTagCompound()) {
                            stack.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
                        }

                        stack.getTagCompound().setInteger("domacSpawnX", pos.getX());
                        stack.getTagCompound().setInteger("domacSpawnY", pos.getY());
                        stack.getTagCompound().setInteger("domacSpawnZ", pos.getZ());

                        playerIn.sendMessage(new net.minecraft.util.text.TextComponentString("§cSpawn set to " + pos.getX() + " " + pos.getY() + " " + pos.getZ()));
                    } else if (this.type == Type.DOMAC_TARGET) {
                        if(!stack.hasTagCompound()) {
                            playerIn.sendMessage(new net.minecraft.util.text.TextComponentString("§cYou need to set the spawn first"));
                        }

                        if(!stack.getTagCompound().hasKey("domacSpawnX")) {
                            playerIn.sendMessage(new net.minecraft.util.text.TextComponentString("§cYou need to set the spawn first"));
                        }


                        BlockPos spawn = new BlockPos(stack.getTagCompound().getInteger("domacSpawnX"), stack.getTagCompound().getInteger("domacSpawnY"), stack.getTagCompound().getInteger("domacSpawnZ"));

                        TileAIPoint te = (TileAIPoint) worldIn.getTileEntity(pos);
                        te.setTarget(Util.blockPosToString(spawn));

                        TileAIPoint teSpawn = (TileAIPoint) worldIn.getTileEntity(spawn);
                        teSpawn.setTarget(Util.blockPosToString(pos));



                        playerIn.sendMessage(new net.minecraft.util.text.TextComponentString("§cTarget set to " + pos));
                    }

                    return true;
                }
            }
        }
        return false;
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAIPoint(this.blockObjectInfo, type);
    }
}
