package fr.yan36.westerlife.common.entities.DynamX;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.joints.Constraint;
import com.jme3.bullet.joints.Point2PointJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.AttachModule;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.AttachedBodySynchronizer;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.DynamXMain;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.network.sync.variables.EntityTransformsVariable;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.joints.EntityJoint;
import fr.dynamx.common.physics.joints.JointHandler;
import fr.dynamx.common.physics.joints.JointHandlerRegistry;
import fr.dynamx.common.physics.utils.RigidBodyTransform;
import fr.dynamx.common.physics.utils.SynchronizedRigidBodyTransform;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.DynamX.TestEntity2;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@SynchronizedEntityVariable.SynchronizedPhysicsModule
public class TestEntityModule2 implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, AttachModule.AttachToSelfModule, AttachedBodySynchronizer   {
    public static final ResourceLocation JOINT_NAME = new ResourceLocation(Main.MODID, "test_joint");

    static {
        JointHandlerRegistry.register(new JointHandler(JOINT_NAME, TestEntity2.class, TestEntity2.class, TestEntityModule2.class));
    }
    public final PhysicsEntity<?> entity;
    private final Map<Byte, PhysicsRigidBody> attachedParts = new HashMap();
    private final HashMap<Byte, SynchronizedRigidBodyTransform> attachedBodiesTransform = new HashMap();
    @SynchronizedEntityVariable(
            name = "part_states"
    )
    private final EntityTransformsVariable synchronizedTransforms;
    public PhysicsRigidBody punchingBag = null;

    public TestEntityModule2(PhysicsEntity<?> entity) {
        this.entity = entity;
        this.synchronizedTransforms = new EntityTransformsVariable(entity, this);
        System.out.println("TestEntityModule2 created");
    }
    @Override

    public boolean canCreateJoint(PhysicsEntity<?> withEntity, byte jointId) {
        System.out.println("canCreateJoint");
        return true;
    }
    @Override
    public void onJointDestroyed(EntityJoint<?> joint) {
        System.out.println("onJointDestroyed");

    }

    @Override
    public void onRemovedFromWorld() {
        if (punchingBag != null) {
            DynamXContext.getPhysicsWorld(entity.world).removeCollisionObject(punchingBag);
            punchingBag = null;
        }
    }

    @Override
    public Constraint createJoint(byte jointId) {

        System.out.println("createJoint");

        CollisionShape shape = new BoxCollisionShape(0.5f, 1.5f, 0.5f);
        punchingBag = DynamXPhysicsHelper.fastCreateRigidBody(entity, 10f, shape, new Vector3f(0,2,0), 0);

        PhysicsRigidBody rigidBody = (PhysicsRigidBody) entity.getPhysicsHandler().getCollisionObject();
        rigidBody.setKinematic(true);
        punchingBag.setPhysicsLocation(rigidBody.getPhysicsLocation(null));
        DynamXContext.getPhysicsWorld(entity.world).addCollisionObject(punchingBag);

        Point2PointJoint joint = new Point2PointJoint(rigidBody, punchingBag, Vector3fPool.get(0, -0.5, 0), Vector3fPool.get(0, 1.5, 0));
        DynamXContext.getPhysicsWorld(entity.world).addJoint(joint);



        return joint;
    }

    /*
        DynamXMain.proxy.scheduleTask(this.entity.world, () -> {
            JointHandlerRegistry.createJointWithSelf(JOINT_NAME, this.entity, (byte) 1);
        });
    * */

    @Override
    public void initPhysicsEntity(@Nullable AbstractEntityPhysicsHandler<?, ?> handler) {
        //if(!this.entity.world.isRemote) {
            System.out.println("initPhysicsEntity called");
            DynamXMain.proxy.scheduleTask(entity.world, () -> { // normalement maintenant le hotswap va marcher
                System.out.println("test ?");
                JointHandlerRegistry.createJointWithSelf(JOINT_NAME, entity, (byte) 0);
            });
       // }
    }

    @Override
    public Map<Byte, SynchronizedRigidBodyTransform> getTransforms() {
        return this.attachedBodiesTransform;
    }

    @Override
    public void setPhysicsTransform(byte b, RigidBodyTransform rigidBodyTransform) {
        System.out.println("setPhysicsTransform called");
        if (this.attachedParts.containsKey(b)) {
            this.attachedBodiesTransform.get(b).getPhysicTransform().set(rigidBodyTransform);
            this.attachedParts.get(b).setPhysicsLocation(rigidBodyTransform.getPosition());
            this.attachedParts.get(b).setPhysicsRotation(rigidBodyTransform.getRotation());
        }
    }
}


