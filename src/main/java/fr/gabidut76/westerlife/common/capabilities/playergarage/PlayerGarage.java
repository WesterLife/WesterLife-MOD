package fr.gabidut76.westerlife.common.capabilities.playergarage;

import fr.aym.acslib.utils.nbtserializer.NBTSerializer;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.gabidut76.westerlife.common.objects.GarageCar;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class PlayerGarage implements IPlayerGarage, INBTSerializable<NBTTagCompound> {

    private List<GarageCar> cars;

    public PlayerGarage(List<GarageCar> cars) {
        this.cars = cars;
    }

    public PlayerGarage() {
        this.cars = new ArrayList<>();
    }



    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("cars", NBTSerializer.serialize(cars));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.cars = (List<GarageCar>) NBTSerializer.unserialize(nbt.getTag("cars"));

    }

    @Override
    public List<GarageCar> getCars() {
        return cars;
    }

    @Override
    public void addCar(GarageCar car) {
        cars.add(car);
    }

    @Override
    public void removeCar(GarageCar car) {
        cars.remove(car);
    }

    @Override
    public void setCars(List<GarageCar> cars) {
        this.cars = cars;
    }

    @Override
    public GarageCar getCar(BaseVehicleEntity car) {
        for(GarageCar garageCar : cars) {
            if(garageCar.getCarNBT().equals(car.getUniqueID())) {
                return garageCar;
            }
        }
        return null;
    }

    @Override
    public void editCar(GarageCar old, GarageCar newCar) {
        if(cars.contains(old)) {
            cars.remove(old);
            cars.add(newCar);
        }
    }
}
