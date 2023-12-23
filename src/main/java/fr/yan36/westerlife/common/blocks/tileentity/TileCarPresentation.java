package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.yan36.westerlife.common.objects.TileToRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

@TileToRegister(location = "westerlife:car_presentation")
public class TileCarPresentation extends TileEntitySyncClient implements ITickable {


    private String car = "";

    public TileCarPresentation() {
        super(null);
    }

    public TileCarPresentation(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.car = tagCompound.getString("car");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("car", car);
        return tagCompound;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        sync();
        markDirty();
        this.car = car;
    }

    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 20 == 0)
            sync();
    }

}
