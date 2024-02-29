package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import net.minecraft.nbt.NBTTagCompound;

@TileToRegister(location = "westerlife:test_sphere")
public class TileTestSphere extends TileEntitySyncClient {



    public TileTestSphere() {
        super(null);
    }

    public TileTestSphere(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        return tagCompound;
    }


}
