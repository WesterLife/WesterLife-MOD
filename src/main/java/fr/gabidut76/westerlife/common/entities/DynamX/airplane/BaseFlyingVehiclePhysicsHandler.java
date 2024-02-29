package fr.gabidut76.westerlife.common.entities.DynamX.airplane;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.bullet.objects.PhysicsVehicle;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.api.physics.IPhysicsWorld;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.DynamXMain;
import fr.dynamx.common.contentpack.type.vehicle.FrictionPoint;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.dynamx.utils.maths.DynamXGeometry;
import fr.dynamx.utils.optimization.QuaternionPool;
import fr.dynamx.utils.optimization.Vector3fPool;
import jme3utilities.math.MyQuaternion;

import java.util.Iterator;

public class BaseFlyingVehiclePhysicsHandler<T extends BaseVehicleEntity<?>> extends BaseVehiclePhysicsHandler<T> {

    private PhysicsVehicle physicsVehicle;

    public BaseFlyingVehiclePhysicsHandler(T entity) {
        super(entity);
    }

    public PhysicsRigidBody createShape(Vector3f position, Quaternion rotation, float spawnRotation) {
        if (MyQuaternion.isZero(rotation)) {
            DynamXMain.log.warn("Resetting physics rotation of entity " + this.handledEntity);
            rotation = DynamXGeometry.rotationYawToQuaternion(spawnRotation);
        }

        Transform transform = new Transform(position, QuaternionPool.get(rotation));
        ModularVehicleInfo modularVehicleInfo = (ModularVehicleInfo)((BaseVehicleEntity)this.getHandledEntity()).getPackInfo();
        this.physicsVehicle = new PhysicsVehicle(modularVehicleInfo.getCollisionsHelper().getPhysicsCollisionShape(), (float)modularVehicleInfo.getEmptyMass());
        this.physicsVehicle.setPhysicsTransform(transform);
        this.physicsVehicle.setUserObject(new BulletShapeType(EnumBulletShapeType.VEHICLE, this.getHandledEntity()));
        this.physicsVehicle.setSleepingThresholds(0.3F, 1.0F);
        return this.physicsVehicle;
    }

    public void addToWorld() {
        IPhysicsWorld physicsWorld = DynamXContext.getPhysicsWorld(((BaseVehicleEntity)this.getHandledEntity()).world);
        if (physicsWorld == null) {
            throw new NullPointerException("Physics world is null, wtf " + ((BaseVehicleEntity)this.handledEntity).getEntityWorld() + " " + this.getCollisionObject());
        } else {
            physicsWorld.addVehicle((PhysicsVehicle)this.getCollisionObject());
        }
    }

    public void update() {
        super.update();
        if (!((ModularVehicleInfo)((BaseVehicleEntity)this.handledEntity).getPackInfo()).getFrictionPoints().isEmpty() && this.isBodyActive()) {
            float horizSpeed = Vector3fPool.get(this.getLinearVelocity().x, 0.0F, this.getLinearVelocity().z).length();
            Iterator var2 = ((ModularVehicleInfo)((BaseVehicleEntity)this.handledEntity).getPackInfo()).getFrictionPoints().iterator();

            while(var2.hasNext()) {
                FrictionPoint f = (FrictionPoint)var2.next();
                Vector3f pushDown = new Vector3f(-this.getLinearVelocity().x, -horizSpeed, -this.getLinearVelocity().z);
                pushDown.multLocal(f.getIntensity());
                this.applyImpulse(f.getPosition(), pushDown);
            }
        }

    }

    public float getSpeed(BaseVehiclePhysicsHandler.SpeedUnit speedUnit) {
        switch (speedUnit) {
            case KMH:
                return this.physicsVehicle.getCurrentVehicleSpeedKmHour();
            case MPH:
                return this.physicsVehicle.getCurrentVehicleSpeedKmHour() * 0.62137F;
            default:
                return -1.0F;
        }
    }

    public void removePhysicsEntity() {
        if (this.physicsVehicle != null) {
            DynamXContext.getPhysicsWorld(this.getHandledEntity().world).removeVehicle(this.physicsVehicle);
        }

    }

    public PhysicsVehicle getPhysicsVehicle() {
        return this.physicsVehicle;
    }
}
