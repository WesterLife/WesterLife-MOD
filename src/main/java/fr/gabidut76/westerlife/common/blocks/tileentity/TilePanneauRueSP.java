package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TilePanneauRueSP extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private String name = "Avenue de la République";

    public TilePanneauRueSP(){
        super(null);
    }

    public TilePanneauRueSP(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.name = tagCompound.getString("name");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("name", this.name);
        return tagCompound;
    }

    public void setName(String name) {
        this.name = name;
        sync();
        markDirty();
    }

    public String getName() {
        return name;
    }

    @Override
    public void update() {
        if(!world.isRemote && world.getWorldTime() % 20 == 0)
            sync();
    }
}
