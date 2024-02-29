package fr.gabidut76.westerlife.common.objects;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;


public class KitWSD extends WorldSavedData {
    public static final String DATA_NAME = Main.MODID + "_WesterLifeKitData";

    private String content;
    public KitWSD(String name) {
        super(DATA_NAME);
    }
    public KitWSD() {
        super(DATA_NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        content = nbt.getString("content");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("content", content);
        return compound;
    }

    public String getContent() {
        return content;
    }
}
