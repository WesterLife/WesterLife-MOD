package fr.yan36.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import fr.yan36.westerlife.common.blocks.dynamx.BlockAIPoint;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileAIPoint extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private BlockAIPoint.Type type;

    public TileAIPoint(){
        super(null);
    }

    public TileAIPoint(BlockObject<?> blockObjectInfo, BlockAIPoint.Type type) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
        this.type = type;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.type = BlockAIPoint.Type.values()[tagCompound.getInteger("type")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("type", this.type.ordinal());
        return tagCompound;
    }

    public BlockAIPoint.Type getType() {
        return type;
    }

    public void setType(BlockAIPoint.Type type) {
        this.type = type;
        sync();
        syncToClient();
    }

    @Override
    public void update() {

    }


    @Override
    public List<IShapeInfo> getUnrotatedCollisionBoxes() {
        return new ArrayList<>();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollision() {
        return new CompoundCollisionShape();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
