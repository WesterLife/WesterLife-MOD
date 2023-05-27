package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileFeuRouge extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private int syncvalue= 20;

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
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("syncvalue", this.syncvalue);
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

    @Override
    public void update() {

    }
}
