package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.gabidut76.westerlife.common.objects.IObjectEditable;
import fr.gabidut76.westerlife.common.objects.ObjectProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TilePanneauRue extends TileEntitySyncClient implements ITickable, IObjectEditable {

    private BlockObject b;

    private String name = "Avenue de la République";
    private BlockPanneauRue.Type type = BlockPanneauRue.Type.WALL;

    public TilePanneauRue(){
        super(null);
    }

    public TilePanneauRue(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.name = tagCompound.getString("name");
        this.type = BlockPanneauRue.Type.valueOf(tagCompound.getString("type"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("name", this.name);
        tagCompound.setString("type", this.type.name());
        return tagCompound;
    }

    public void setName(String name) {
        this.name = name;
        sync();
        markDirty();
    }

    public BlockPanneauRue.Type getType() {
        return type;

    }

    public void setType(BlockPanneauRue.Type type) {
        this.type = type;
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

    @Override
    public List<ObjectProperty> getEditableProperties() {
        return new ArrayList<ObjectProperty>() {{
            add(new ObjectProperty("name", getName(), ObjectProperty.Type.STRING));
            add(new ObjectProperty("type", getName(), ObjectProperty.Type.STRING));
        }};
    }

    @Override
    public void onPropertyChange(ObjectProperty value) {
        switch (value.getName()) {
            case "name":
                setName(value.getValue());
                break;
            case "type":
                setType(BlockPanneauRue.Type.valueOf(value.getValue()));
                break;
        }
    }
}
