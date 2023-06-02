package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileSpot extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private int angle;

    public TileSpot(){
        super(null);
    }

    public TileSpot(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.angle = tagCompound.getInteger("angle");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("angle", this.angle);
        return tagCompound;
    }


    public void setAngle(int angle) {
        this.angle = angle;
        sync();
        markDirty();
    }

    public int getAngle() {
        return angle;
    }

    @Override
    public void update() {

    }
}
