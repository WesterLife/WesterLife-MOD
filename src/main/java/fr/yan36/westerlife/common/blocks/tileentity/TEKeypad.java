package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TEKeypad extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private static String code="";

    public TEKeypad(){
        super(null);
    }

    public TEKeypad(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        code = tagCompound.getString("0000");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("0000",code);
        return tagCompound;
    }

    public void setCode(String code) {
        TEKeypad.code = code;
    }

    public static String getCode() {
        return code;
    }

    @Override
    public void update() {
    }
}
