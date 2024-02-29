package fr.gabidut76.westerlife.common.capabilities.playergarage;

import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.gabidut76.westerlife.common.objects.GarageCar;

import java.util.List;

public interface IPlayerGarage {
    List<GarageCar> getCars();
    void addCar(GarageCar car);
    void removeCar(GarageCar car);
    void setCars(List<GarageCar> cars);

    GarageCar getCar(BaseVehicleEntity car);
    void editCar(GarageCar old, GarageCar newCar);
}
