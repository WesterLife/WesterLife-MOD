package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TilePorteNom extends TileEntitySyncClient implements ITickable {

    private BlockObject b;
    private String text="";

    public TilePorteNom(){
        super(null);
    }

    public TilePorteNom(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        text = tagCompound.getString("text");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("text",text);
        return tagCompound;
    }

    public void setText(String text) {
        this.text = text;
        sync();
        markDirty();
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void update() {
    }
}
