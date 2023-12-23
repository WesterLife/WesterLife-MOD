package fr.yan36.westerlife.common.entities.DynamX.clotheentity;


import com.jme3.bullet.PhysicsSoftSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.infos.IndexedMesh;
import com.jme3.bullet.joints.Point2PointJoint;
import com.jme3.bullet.objects.PhysicsBody;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.bullet.objects.PhysicsSoftBody;
import com.jme3.bullet.objects.infos.Aero;
import com.jme3.bullet.objects.infos.Sbcp;
import com.jme3.bullet.objects.infos.SoftBodyConfig;
import com.jme3.bullet.objects.infos.SoftBodyMaterial;
import com.jme3.bullet.util.NativeSoftBodyUtil;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.entities.EntityPhysicsHandler;
import fr.dynamx.common.physics.entities.RagdollPhysics;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.common.utils.physics.PhysicsShapes;

public class ClothEntityPhysicsHandler extends AbstractEntityPhysicsHandler<ClothEntity, PhysicsSoftBody> {

    public ClothEntity entity;

    private final Vector3f linearVel = new Vector3f();
    private final Vector3f rotationalVel = new Vector3f();

    public ClothEntityPhysicsHandler(ClothEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    protected PhysicsSoftBody createShape(Vector3f vector3f, Quaternion quaternion, float v) {


//
//        System.out.println("initPhysicsEntity called");
//
//        ClothEntityModule module = entity.getModuleByType(ClothEntityModule.class);
//
//        if(module == null) {
//            System.out.println("module is null");
//            return null;
//        }
//
//        PhysicsSoftSpace dynamicsWorld = DynamXContext.getPhysicsWorld(entity.world).getDynamicsWorld();
//
//        IndexedMesh clothGrid = PhysicsShapes.createClothGrid(9, 9, 0.5f);
//        module.cloth = new PhysicsSoftBody();
//
//        NativeSoftBodyUtil.appendFromNativeMesh(clothGrid, module.cloth);
//        module.cloth.setMargin(0.50f);
//        module.cloth.setMass(50);
//
//        int nodeIndex = 0;
//        module.cloth.setNodeMass(nodeIndex, PhysicsBody.massForStatic);
//
//        SoftBodyConfig config = module.cloth.getSoftConfig();
//        config.set(Sbcp.Damping, 0.01f);
//        config.set(Sbcp.Drag, 0.5f);
//        config.set(Sbcp.Lift, 10f);
//        config.setAerodynamics(Aero.F_TwoSidedLiftDrag);
//        config.set(Sbcp.PoseMatching, 0.05f);
//        config.setPositionIterations(10);
//
//        SoftBodyMaterial softMaterial = module.cloth.getSoftMaterial();
//        softMaterial.setAngularStiffness(0f);
//
//        Quaternion rotation = new Quaternion();
//        rotation.fromAngles(FastMath.HALF_PI, 0f, 0f);
//        module.cloth.generateClusters();
//
//        module.cloth.setPhysicsLocation(DynamXUtils.toVector3f(entity.getPosition()));
//
//
//        dynamicsWorld.addCollisionObject(module.cloth);
//
//
//
//
//
//
//        module.cloth.applyTranslation(new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ));

        PhysicsSoftBody softBody = new PhysicsSoftBody();
        softBody.setUserObject(new BulletShapeType<>(EnumBulletShapeType.BULLET_ENTITY, handledEntity, softBody.getCollisionShape()));
        NativeSoftBodyUtil.appendFromNativeMesh(PhysicsShapes.createClothGrid(9, 9, 0.5f), softBody);

        softBody.setPose(false, true);
        SoftBodyConfig config = softBody.getSoftConfig();
        config.set(Sbcp.PoseMatching, 0.05f);

        softBody.setPhysicsLocation(vector3f);


        softBody.setCcdSweptSphereRadius(0.7f);
        softBody.setCcdMotionThreshold(0.7f);
        softBody.setMargin(0.1f);
        softBody.setNodeMass(0, 0);
        return softBody;




    }

    @Override
    public void setPhysicsPosition(Vector3f position) {
        handledEntity.physicsPosition.set(position);
        collisionObject.setPhysicsLocation(position);
    }

    @Override
    public void setPhysicsRotation(Quaternion rotation) {
        handledEntity.physicsRotation.set(rotation);
        collisionObject.applyRotation(rotation);
    }

    @Override
    public Vector3f getLinearVelocity() {
        return linearVel;
    }

    @Override
    public Vector3f getAngularVelocity() {
        return rotationalVel;
    }

    @Override
    public void setLinearVelocity(Vector3f velocity) {
        this.linearVel.set(velocity);
        //collisionObject.vel(velocity);
    }

    @Override
    public void setAngularVelocity(Vector3f velocity) {
        this.rotationalVel.set(velocity);
        //collisionObject.setAngularVelocity(velocity);
    }

    @Override
    public void applyForce(Vector3f at, Vector3f force) {
        //getCollisionObject().applyForce(force, at);

    }

    @Override
    public void applyTorque(Vector3f force) {

    }

    @Override
    public void applyImpulse(Vector3f at, Vector3f force) {

    }

    @Override
    public void applyTorqueImpulse(Vector3f force) {

    }

    @Override
    public void setFreezePhysics(boolean freeze) {

    }

}
