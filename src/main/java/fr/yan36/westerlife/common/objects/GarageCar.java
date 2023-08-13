package fr.yan36.westerlife.common.objects;

import fr.nathanael2611.simpledatabasemanager.core.StoredData;
import fr.nathanael2611.simpledatabasemanager.util.ISaveDatabase;
import net.minecraft.nbt.NBTTagCompound;

public class GarageCar extends StoredData implements ISaveDatabase {

    private String owner;
    private String carName;
    private String carPlate;
    private byte meta;


    public GarageCar(String owner, String carName, String carPlate, byte meta) {
        this.owner = owner;
        this.carName = carName;
        this.carPlate = carPlate;
        this.meta = meta;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("owner", owner);
        nbtTagCompound.setString("carName", carName);
        nbtTagCompound.setString("carPlate", carPlate);
        nbtTagCompound.setByte("meta", meta);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        owner = nbtTagCompound.getString("owner");
        carName = nbtTagCompound.getString("carName");
        carPlate = nbtTagCompound.getString("carPlate");
        meta = nbtTagCompound.getByte("meta");
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public byte getMeta() {
        return meta;
    }

    public void setMeta(byte meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        // make it parseable
        return owner + ";" + carName + ";" + carPlate + ";" + meta;
    }

    public static GarageCar fromString(String str) {
        System.out.println(str);
        String[] split = str.split(";");
        return new GarageCar(split[0], split[1], split[2], Byte.parseByte(split[3]));
    }
}
