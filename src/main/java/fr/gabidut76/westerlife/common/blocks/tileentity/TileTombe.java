package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;


public class TileTombe extends TileEntitySyncClient implements ITickable {
    private String text = "Quelqu'un.";
    public TileTombe(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileTombe() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.text = tagCompound.getString("text");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("text", this.text);
        return tagCompound;
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            if(world.getWorldTime() % 20 == 0) {
                sync();
            }
        }



    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
