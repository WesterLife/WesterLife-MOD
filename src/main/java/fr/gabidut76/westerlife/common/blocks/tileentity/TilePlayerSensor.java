package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.gabidut76.westerlife.common.blocks.BlockPlayerSensor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TilePlayerSensor extends TileEntity implements ITickable {
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return false;
    }
    private int radius = 5;
    private int timepowered = 30;
    private String playerexcluded = "";

    public TilePlayerSensor() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.radius = tagCompound.getInteger("radius");
        this.playerexcluded = tagCompound.getString("playerexcluded");
        this.timepowered = tagCompound.getInteger("timepowered");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("radius", radius);
        tagCompound.setString("playerexcluded", playerexcluded);
        tagCompound.setInteger("timepowered", timepowered);
        return tagCompound;
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            AxisAlignedBB aabb = new AxisAlignedBB(this.pos).grow(getRadius());
            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);

            for(EntityPlayer player : players) {
                List<String> excluded = playerexcluded.isEmpty() ? Collections.emptyList() : playerexcluded.contains(";") ? Arrays.asList(playerexcluded.split(";")) : Collections.singletonList(playerexcluded);
                if(!excluded.contains(player.getName()) && !world.getBlockState(pos).getValue(BlockPlayerSensor.POWERED)) {
                    BlockPlayerSensor block = (BlockPlayerSensor) this.getBlockType();
                    block.setPowered(world, pos, world.getBlockState(pos), getTimepowered());
                    System.out.println("Player detected");
                }
            }
        }
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getPlayerexcluded() {
        return playerexcluded;
    }

    public void setPlayerexcluded(String playerexcluded) {
        this.playerexcluded = playerexcluded;
    }

    public int getTimepowered() {
        return timepowered;
    }

    public void setTimepowered(int timepowered) {
        this.timepowered = timepowered;
    }
}
