package fr.yan36.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.utils.AABB;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public class TilePark extends TileEntitySyncClient {

    private BlockPos linkedTo = new BlockPos(-1,-1,-1);

    public TilePark(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TilePark() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        linkedTo = Util.parseBlockPosFromString(tagCompound.getString("linkedTo"));
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("linkedTo", Util.blockPosToString(linkedTo));
        return tagCompound;
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
    public List<IShapeInfo> getUnrotatedCollisionBoxes() {
        return new ArrayList<>();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollision() {
        return new CompoundCollisionShape();
    }

}
