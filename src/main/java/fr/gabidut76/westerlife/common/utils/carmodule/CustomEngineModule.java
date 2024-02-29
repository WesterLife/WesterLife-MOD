package fr.gabidut76.westerlife.common.utils.carmodule;

import fr.dynamx.common.contentpack.type.vehicle.CarEngineInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;

import fr.dynamx.common.entities.modules.engines.CarEngineModule;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;

public class CustomEngineModule extends CarEngineModule {
    public CustomEngineModule(BaseVehicleEntity<? extends BaseVehiclePhysicsHandler<?>> entity, CarEngineInfo engineInfo) {
        super(entity, engineInfo);
    }
}
