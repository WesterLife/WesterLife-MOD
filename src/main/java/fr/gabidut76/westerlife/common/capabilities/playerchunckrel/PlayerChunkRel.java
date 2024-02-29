package fr.gabidut76.westerlife.common.capabilities.playerchunckrel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerChunkRel implements IPlayerChunk, INBTSerializable<NBTTagCompound> {

    private int pollen;
    private int co2;

    public PlayerChunkRel(int pollen, int co2) {
        this.pollen = pollen;
        this.co2 = co2;
    }

    public PlayerChunkRel() {
        this(0, 0);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("pollen", pollen);
        nbt.setInteger("co2", co2);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.pollen = nbt.getInteger("pollen");
        this.co2 = nbt.getInteger("co2");
    }


    @Override
    public void setPollen(int pollen) {
        this.pollen = pollen;
    }

    @Override
    public int getPollen() {
        return pollen;
    }

    @Override
    public void setCO2(int co2) {
        this.co2 = co2;
    }

    @Override
    public int getCO2() {
        return co2;
    }
}
