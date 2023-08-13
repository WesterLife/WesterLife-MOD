package fr.yan36.westerlife.common.utils.carmodule;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.EntityVariable;
import fr.dynamx.api.network.sync.SynchronizationRules;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.entities.modules.CarEngineModule;
import fr.dynamx.common.entities.modules.WheelsModule;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.dynamx.common.physics.entities.modules.EnginePhysicsHandler;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.utils.CarControls;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = Main.MODID)
public class AICarEngineModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, IPhysicsModule.IEntityUpdateListener {

    private final PackPhysicsEntity<?, ?> entity;


    @SynchronizedEntityVariable(name = "activate")
    private final EntityVariable<Boolean> activate = new EntityVariable<>(SynchronizationRules.PHYSICS_TO_SPECTATORS, false);

    @SynchronizedEntityVariable(name = "step")
    private final EntityVariable<Integer> step = new EntityVariable<>(SynchronizationRules.PHYSICS_TO_SPECTATORS, 0);

    public AICarEngineModule(PackPhysicsEntity<?, ?> entity) {
        this.entity = entity;
    }



    public boolean isActivate() {
        return activate.get();
    }

    public void setActivate(boolean act) {
        this.activate.set(act);
    }

    public Integer getStep() {
        return step.get();
    }

    public void setStep(Integer act) {
        this.step.set(act);
    }


    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("activate", activate.get());
        tag.setInteger("step", step.get());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        activate.set(tag.getBoolean("activate"));
        step.set(tag.getInteger("step"));
    }


    @Override
    public void updateEntity() {
        if (entity instanceof CarEntity) {
            CarEntity<?> carEntity = (CarEntity<?>) entity;
            CarEngineModule module = carEntity.getModuleByType(CarEngineModule.class);
            if(module != null) {
                if (isActivate()) {
                    if ((carEntity).physicsHandler != null) {
                        CarEngineModule engine = carEntity.getModuleByType(CarEngineModule.class);
                        engine.setSpeedLimit(20);

                        BlockPos nearestpoint = Util.getNearestBlockAIPoint(entity.world, entity.getPosition(), 10);

                        if(getStep() == 0) {
                            carEntity.setGlowing(true);

                            int angle = Util.getAngleBetweenTwoPoint(entity.getPosition(), nearestpoint);
                            if(angle == 0) {
                                angle = 1;
                            }

                            carEntity.getPhysicsHandler().setPhysicsPosition(new Vector3f(nearestpoint.getX(), nearestpoint.getY(), nearestpoint.getZ()));
                            carEntity.getPhysicsHandler().setPhysicsRotation(new Quaternion(10,10,10,10));
                            System.out.println(nearestpoint);
                            setStep(1);
                        }

                        if(getStep() == 1) {
//                            int angle = Util.getAngleBetweenTwoPoint(entity.getPosition(), nearestpoint);
//
//                            if (angle > 180) {
//                                engine.setControls(CarControls.ENGINE_STARTED.getValue() | CarControls.ACCELERATING.getValue() | CarControls.TURNING_RIGHT.getValue());
//                            } else {
//                                engine.setControls(CarControls.ENGINE_STARTED.getValue() | CarControls.ACCELERATING.getValue() | CarControls.TURNING_LEFT.getValue());
//                            }
//
//                            System.out.println(angle);
                        }



                    }
                }
            }
        }
    }


}