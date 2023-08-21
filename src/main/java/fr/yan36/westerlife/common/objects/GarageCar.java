package fr.yan36.westerlife.common.objects;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class GarageCar implements ISerializable, INBTSerializable, ISerializablePacket {

    private String owner;
    private String carName;
    private String carPlate;
    private int meta;
    private boolean isInGarage;
    private NBTTagCompound carNBT;
    private String UniqueID;


    public GarageCar() {
    }

    public GarageCar(NBTTagCompound nbt) {
        read(nbt);
    }

    public GarageCar(String owner, String carName, String carPlate, int meta, boolean isInGarage, NBTTagCompound carNBT, String UniqueID) {
        this.owner = owner;
        this.carName = carName;
        this.carPlate = carPlate;
        this.meta = meta;
        this.isInGarage = isInGarage;
        this.carNBT = carNBT;
        this.UniqueID = UniqueID;
    }

    public String getOwner() {
        return owner;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public int getMeta() {
        return meta;
    }

    public boolean isInGarage() {
        return isInGarage;
    }

    public NBTTagCompound getCarNBT() {
        return carNBT;
    }

    public void setInGarage(boolean inGarage) {
        isInGarage = inGarage;
    }

    @Override
    public void write(NBTTagCompound to) {
        ISerializable.super.write(to);
    }

    @Override
    public void read(NBTTagCompound from) {
        ISerializable.super.read(from);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{owner, carName, carPlate, meta, isInGarage, carNBT, UniqueID};
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String uniqueID) {
        UniqueID = uniqueID;
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        owner = (String) objects[0];
        carName = (String) objects[1];
        carPlate = (String) objects[2];
        meta = (int) objects[3];
        isInGarage = Boolean.parseBoolean(String.valueOf(objects[4]));
        System.out.println("isInGarage = " + isInGarage);
        carNBT = (NBTTagCompound) objects[5];
        UniqueID = (String) objects[6];
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("owner", owner);
        nbt.setString("carName", carName);
        nbt.setString("carPlate", carPlate);
        nbt.setInteger("meta", meta);
        nbt.setBoolean("isInGarage", isInGarage);
        nbt.setTag("carNBT", carNBT);
        nbt.setString("UniqueID", UniqueID);
        return null;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;
        owner = tag.getString("owner");
        carName = tag.getString("carName");
        carPlate = tag.getString("carPlate");
        meta = tag.getInteger("meta");
        isInGarage = tag.getBoolean("isInGarage");
        carNBT = tag.getCompoundTag("carNBT");
        UniqueID = tag.getString("UniqueID");
    }

}
