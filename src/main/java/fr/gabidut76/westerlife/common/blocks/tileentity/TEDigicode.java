package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;

public class TEDigicode extends TileEntitySyncClient {

    private BlockObject b;

    private static String code="0000";

    public TEDigicode(){
        super(null);
    }

    public TEDigicode(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        code = tagCompound.getString("code");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("code", code);
        return tagCompound;
    }

    public void setCode(String code) {
        TEDigicode.code = code;
        sync();
        markDirty();
    }

    public static String getCode() {
        return code;
    }


}
