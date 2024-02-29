package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TileGarage extends TileEntitySyncClient implements ITickable {

    private Boolean isLinked = false;
    private BlockPos linkedTo = new BlockPos(-1,-1,-1);

    public TileGarage(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileGarage() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.isLinked = tagCompound.getBoolean("isLinked");
        this.linkedTo = Util.parseBlockPosFromString(tagCompound.getString("linkedTo"));
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("isLinked", isLinked);
        tagCompound.setString("linkedTo", Util.blockPosToString(linkedTo));
        return tagCompound;
    }

    @Override
    public void update() {
    }

    public void setIsLinked(boolean linkedTo) {
        this.isLinked = linkedTo;
        sync();
        syncToClient();
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinkedTo(BlockPos linkedTo) {
        this.linkedTo = linkedTo;
        sync();
        syncToClient();
    }

    public BlockPos getLinkedTo() {
        return linkedTo;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollision() {
        return super.getPhysicsCollision();
    }
}
