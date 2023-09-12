package fr.yan36.westerlife.common.entities.DynamX.warningsign;

import com.jme3.bullet.RotationOrder;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.joints.Constraint;
import com.jme3.bullet.joints.New6Dof;
import com.jme3.bullet.joints.Point2PointJoint;
import com.jme3.bullet.joints.motors.MotorParam;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.AttachModule;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.AttachedBodySynchronizer;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.DynamXMain;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.entities.vehicles.DoorEntity;
import fr.dynamx.common.network.sync.variables.EntityTransformsVariable;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.joints.EntityJoint;
import fr.dynamx.common.physics.joints.JointHandler;
import fr.dynamx.common.physics.joints.JointHandlerRegistry;
import fr.dynamx.common.physics.utils.RigidBodyTransform;
import fr.dynamx.common.physics.utils.SynchronizedRigidBodyTransform;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.Main;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = Main.MODID)
public class WarningSignEntityModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, AttachModule.AttachToSelfModule, AttachedBodySynchronizer   {
    public static final ResourceLocation JOINT_NAME = new ResourceLocation(Main.MODID, "warning_sign_joint");

    static {
        JointHandlerRegistry.register(new JointHandler(JOINT_NAME, WarningSignEntity.class, WarningSignEntity.class, WarningSignEntityModule.class));
    }
    public final PhysicsEntity<?> entity;
    private final Map<Byte, PhysicsRigidBody> attachedParts = new HashMap();
    private final HashMap<Byte, SynchronizedRigidBodyTransform> attachedBodiesTransform = new HashMap();
    @SynchronizedEntityVariable(
            name = "part_states"
    )
    private final EntityTransformsVariable synchronizedTransforms;
    public PhysicsRigidBody punchingBag = null;

    public WarningSignEntityModule(PhysicsEntity<?> entity) {
        this.entity = entity;
        this.synchronizedTransforms = new EntityTransformsVariable(entity, this);
        System.out.println("WarningSignEntityModule created");
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

        CollisionShape shape = new BoxCollisionShape(0.3f, .7f, 0.2f);
        shape.setMargin(0.04f);
        punchingBag = DynamXPhysicsHelper.fastCreateRigidBody(entity, 10f, shape, new Vector3f(0,1f,0), 0);

        PhysicsRigidBody rigidBody = (PhysicsRigidBody) entity.getPhysicsHandler().getCollisionObject();
        rigidBody.setKinematic(true);
        punchingBag.setPhysicsLocation(rigidBody.getPhysicsLocation(null).addLocal(0,1,0));
        punchingBag.setCcdMotionThreshold(0.5f);
        punchingBag.setCcdSweptSphereRadius(0.5f);
        DynamXContext.getPhysicsWorld(entity.world).addCollisionObject(punchingBag);
        New6Dof joint2 = new New6Dof(rigidBody, punchingBag, Vector3fPool.get(0, 0, 0), Vector3fPool.get(0, -1, 0), Quaternion.IDENTITY.toRotationMatrix(), Quaternion.IDENTITY.toRotationMatrix(), RotationOrder.XYZ);
        DynamXContext.getPhysicsWorld(entity.world).addJoint(joint2);

        joint2.setCollisionBetweenLinkedBodies(false);
        joint2.setBreakingImpulseThreshold(75f);
        joint2.set(MotorParam.Damping, DynamXPhysicsHelper.EnumPhysicsAxis.Y_ROT.ordinal(), 10f);
        joint2.set(MotorParam.Damping, DynamXPhysicsHelper.EnumPhysicsAxis.X_ROT.ordinal(), 10);
        joint2.set(MotorParam.Damping, DynamXPhysicsHelper.EnumPhysicsAxis.Z_ROT.ordinal(), 10f);

        joint2.enableSpring(DynamXPhysicsHelper.EnumPhysicsAxis.Y_ROT.ordinal(), true);
        joint2.enableSpring(DynamXPhysicsHelper.EnumPhysicsAxis.X_ROT.ordinal(), true);
        joint2.enableSpring(DynamXPhysicsHelper.EnumPhysicsAxis.Z_ROT.ordinal(), true);


        joint2.set(MotorParam.LowerLimit, DynamXPhysicsHelper.EnumPhysicsAxis.X_ROT.ordinal(), 0);
        joint2.set(MotorParam.UpperLimit, DynamXPhysicsHelper.EnumPhysicsAxis.X_ROT.ordinal(), 0);
        joint2.set(MotorParam.LowerLimit, DynamXPhysicsHelper.EnumPhysicsAxis.Y_ROT.ordinal(), 0);
        joint2.set(MotorParam.UpperLimit, DynamXPhysicsHelper.EnumPhysicsAxis.Y_ROT.ordinal(), 0);
        joint2.set(MotorParam.LowerLimit, DynamXPhysicsHelper.EnumPhysicsAxis.Z_ROT.ordinal(), 0);
        joint2.set(MotorParam.UpperLimit, DynamXPhysicsHelper.EnumPhysicsAxis.Z_ROT.ordinal(), 0);

        joint2.set(MotorParam.StopErp, DynamXPhysicsHelper.EnumPhysicsAxis.X_ROT.ordinal(), 0.8f);
        joint2.set(MotorParam.StopErp, DynamXPhysicsHelper.EnumPhysicsAxis.Y_ROT.ordinal(), 0.8f);
        joint2.set(MotorParam.StopErp, DynamXPhysicsHelper.EnumPhysicsAxis.Z_ROT.ordinal(), 0.8f);


        return joint2;
    }

    @Override
    public void initPhysicsEntity(@Nullable AbstractEntityPhysicsHandler<?, ?> handler) {
            System.out.println("initPhysicsEntity called");
            DynamXMain.proxy.scheduleTask(entity.world, () -> {
                System.out.println("test ?");
                JointHandlerRegistry.createJointWithSelf(JOINT_NAME, entity, (byte) 0);
            });
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


