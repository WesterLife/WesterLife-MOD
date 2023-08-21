package fr.yan36.westerlife.common.capabilities.playergarage;

import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.utils.Animation;

import java.util.List;

public interface IPlayerGarage {
    List<GarageCar> getCars();
    void addCar(GarageCar car);
    void removeCar(GarageCar car);
    void setCars(List<GarageCar> cars);

    GarageCar getCar(BaseVehicleEntity car);
    void editCar(GarageCar old, GarageCar newCar);
}
