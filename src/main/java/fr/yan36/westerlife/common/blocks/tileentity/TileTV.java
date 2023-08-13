package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.yan36.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.yan36.westerlife.common.objects.IObjectEditable;
import fr.yan36.westerlife.common.objects.ObjectProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TileTV extends TileEntitySyncClient implements ITickable {


    private String url = "https://google.com";

    public TileTV() {
        super(null);
    }

    public TileTV(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.url = tagCompound.getString("name");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("url", this.url);
        return tagCompound;
    }


    public void setUrl(String url) {
        this.url = url;
        sync();
        markDirty();
    }

    public String getUrl() {
        return url;
    }


    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 20 == 0)
            sync();
    }

}
