package fr.yan36.westerlife.common.objects;

import com.google.gson.JsonObject;
import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class GarageCar implements ISerializablePacket {

    private String owner;
    private String carName;
    private String carPlate;
    private int meta;
    private boolean isInGarage;
    private NBTTagCompound carNBT;
    private String UniqueID;


    public GarageCar() {
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
    public Object[] getObjectsToSave() {
        System.out.println(owner + " " + carName + " " + carPlate + " " + meta + " " + isInGarage + " " + carNBT + " " + UniqueID);
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
        carNBT = (NBTTagCompound) objects[5];
        UniqueID = (String) objects[6];
    }


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", carName);
        json.addProperty("nbt", carNBT.toString());
        json.addProperty("meta", meta);
        json.addProperty("carplate", carPlate);
        json.addProperty("uid", UniqueID);
        json.addProperty("isInGarage", isInGarage);
        json.addProperty("carname", carName);
        json.addProperty("owner", owner);
        return json;
    }

    public static GarageCar fromJson(JsonObject json) throws NBTException {
        GarageCar car = new GarageCar();
        car.carName = json.get("name").getAsString();
        car.carNBT = JsonToNBT.getTagFromJson(json.get("nbt").getAsString());
        car.meta = json.get("meta").getAsInt();
        car.carPlate = json.get("carplate").getAsString();
        car.UniqueID = json.get("uid").getAsString();
        car.isInGarage = json.get("isInGarage").getAsBoolean();
        car.carName = json.get("carname").getAsString();
        car.owner = json.get("owner").getAsString();
        return car;
    }

}
