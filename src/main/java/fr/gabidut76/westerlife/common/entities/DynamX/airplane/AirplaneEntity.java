package fr.gabidut76.westerlife.common.entities.DynamX.airplane;

import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.IModuleContainer;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.common.entities.modules.WheelsModule;
import fr.dynamx.common.physics.entities.modules.WheelsPhysicsHandler;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class AirplaneEntity<T extends AirplaneEntity.AirplanePhysicsHandler<?>> extends BaseVehicleEntity<T> implements IModuleContainer.ISeatsContainer {
    private SeatsModule seats;
    private WheelsModule wheels;

    public AirplaneEntity(World world) {
        super(world);
    }


    public AirplaneEntity(String name, World world, Vector3f pos, float spawnRotationAngle, int metadata) {
        super(name, world, pos, spawnRotationAngle, metadata);
    }

    public ModularVehicleInfo createInfo(String infoName) {
        return (ModularVehicleInfo) Main.AIRPLANES_INFO.findInfo(infoName);
    }

    public T createPhysicsHandler() {
        return (T) new AirplanePhysicsHandler(this);
    }

    @Nonnull
    public WheelsModule getWheels() {
        return this.wheels;
    }

    @Nonnull
    public SeatsModule getSeats() {
        return this.seats;
    }

    public PackPhysicsEntity<?, ?> cast() {
        return this;
    }

    protected void sortModules() {
        super.sortModules();
        this.seats = (SeatsModule)this.getModuleByType(SeatsModule.class);
        this.wheels = (WheelsModule)this.getModuleByType(WheelsModule.class);
    }

    public static class AirplanePhysicsHandler<A extends AirplaneEntity<?>> extends BaseFlyingVehiclePhysicsHandler<A> {
        public AirplanePhysicsHandler(A entity) {
            super(entity);
        }

        public WheelsPhysicsHandler getWheels() {
            return ((AirplaneEntity)this.getHandledEntity()).getWheels().getPhysicsHandler();
        }
    }
}
