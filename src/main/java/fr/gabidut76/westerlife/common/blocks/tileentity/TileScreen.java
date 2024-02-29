package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileScreen extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private String url = "";

    public TileScreen(){
        super(null);
    }

    public TileScreen(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.url = tagCompound.getString("url");
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

    }
}
