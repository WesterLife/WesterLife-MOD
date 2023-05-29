package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileFeuRouge extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private int syncvalue= 20;
    private int position = 0;

    public TileFeuRouge(){
        super(null);
    }

    public TileFeuRouge(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.syncvalue = tagCompound.getInteger("syncvalue");
        this.position = tagCompound.getInteger("position");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("syncvalue", this.syncvalue);
        tagCompound.setInteger("position", this.position);
        return tagCompound;
    }


    public void setSyncvalue(int syncvalue2) {
        this.syncvalue = syncvalue2;
        sync();
        markDirty();
    }

    public int getSyncvalue() {
        return this.syncvalue;
    }

    public void setPosition(int position) {
        this.position = position;
        sync();
        markDirty();
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void update() {

    }
}
