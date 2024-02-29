package fr.gabidut76.westerlife.common.entities.DynamX.airplane.infoloader;

import fr.aym.acslib.api.services.error.ErrorLevel;
import fr.dynamx.common.contentpack.parts.PartWheel;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.contentpack.type.vehicle.TrailerAttachInfo;
import fr.dynamx.common.contentpack.type.vehicle.VehicleValidator;
import fr.dynamx.common.items.DynamXItemSpawner;
import fr.dynamx.common.items.vehicle.ItemTrailer;
import fr.dynamx.utils.errors.DynamXErrorManager;

public class AirplaneVehicleValidator implements VehicleValidator {
    public DynamXItemSpawner<ModularVehicleInfo> getSpawnItem(ModularVehicleInfo info) {
        return new ItemAirplane(info);
    }

    public void validate(ModularVehicleInfo info) {
        if (info.getPartsByType(PartWheel.class).isEmpty()) {
            DynamXErrorManager.addPackError(info.getPackName(), "config_error", ErrorLevel.FATAL, info.getName(), "This airplane has no wheels !");
        }

        //TODO: check config
//        if (info.getSubPropertyByType(TrailerAttachInfo.class) == null) {
//            DynamXErrorManager.addPackError(info.getPackName(), "config_error", ErrorLevel.FATAL, info.getName(), "Missing trailer config !");
//        }

    }
}
