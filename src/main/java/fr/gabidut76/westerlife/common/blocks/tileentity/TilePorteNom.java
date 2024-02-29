package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TilePorteNom extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private String name = "Mr. Nobody";
    private String function = "President of the United States";

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
        this.name = tagCompound.getString("name");
        this.function = tagCompound.getString("function");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("name", this.name);
        tagCompound.setString("function", this.function);
        return tagCompound;
    }


    public void setFunction(String function) {
        this.function = function;
        sync();
        markDirty();
    }

    public String getFunction() {
        return function;
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
