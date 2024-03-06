package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

@TileToRegister(location = "westerlife:coke_table")
public class TileCokeTable extends TileEntitySyncClient implements ITickable {
    public long placedAt = 0;
    public int placedLeavesInPot = 1;
    public boolean isResinReady = false;

    public TileCokeTable(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileCokeTable() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        placedAt = tagCompound.getLong("placedAt");
        placedLeavesInPot = tagCompound.getInteger("placedLeavesInPot");
        isResinReady = tagCompound.getBoolean("isResinReady");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setLong("placedAt", placedAt);
        tagCompound.setInteger("placedLeavesInPot", placedLeavesInPot);
        tagCompound.setBoolean("isResinReady", isResinReady);
        super.writeToNBT(tagCompound);
        return tagCompound;
    }

    @Override
    public void update() {
//        if (!world.isRemote && placedAt == 0) {
//            placedAt = world.getTotalWorldTime();
//            sync();
//        }
        if(!world.isRemote && world.getTotalWorldTime() - placedAt > (20 * 10) && placedLeavesInPot == 6) {
            placedLeavesInPot = 7;
            sync();
        }
    }

}
