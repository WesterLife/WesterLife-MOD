package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;


public class TileChair extends TileEntitySyncClient implements ITickable {
    private int color = 0xFFFFFF;
    public TileChair(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileChair() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.color = tagCompound.getInteger("color");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("color", color);
        return tagCompound;
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            if(world.getWorldTime() % 20 == 0) {
                sync();
            }
        }



        super.update();
    }

    public void setColor(int color) {
        this.color = color;
        sync();
        markDirty();
    }

    public int getColor() {
        return color;
    }
}
