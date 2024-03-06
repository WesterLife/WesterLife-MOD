package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

@TileToRegister(location = "westerlife:coke")
public class TileCoke extends TileEntitySyncClient implements ITickable {
    public long placedAt = 0;
    public boolean hasLeaves = false;

    public TileCoke(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileCoke() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        placedAt = tagCompound.getLong("placedAt");
        hasLeaves = tagCompound.getBoolean("hasLeaves");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setLong("placedAt", placedAt);
        tagCompound.setBoolean("hasLeaves", hasLeaves);
        super.writeToNBT(tagCompound);
        return tagCompound;
    }

    @Override
    public void update() {
        if (!world.isRemote && placedAt == 0) {
            if(hasLeaves) {
                System.out.println("change !");
                placedAt = world.getTotalWorldTime();
            }
            sync();
        }
    }

}
