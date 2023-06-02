package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TESign extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private String text="";
    private String color="black";

    public TESign(){
        super(null);
    }

    public TESign(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        text = tagCompound.getString("text");
        color = tagCompound.getString("color");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("text",text);
        tagCompound.setString("color",color);
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
        sync();
        markDirty();
    }

    @Override
    public void update() {
    }
}
