package fr.yan36.westerlife.common.entities.DynamX.clotheentity;

import com.jme3.bullet.PhysicsSoftSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.infos.IndexedMesh;
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
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.AttachedBodySynchronizer;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.client.renders.mesh.shapes.GridGLMesh;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.network.sync.variables.EntityTransformsVariable;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.joints.JointHandler;
import fr.dynamx.common.physics.joints.JointHandlerRegistry;
import fr.dynamx.common.physics.utils.RigidBodyTransform;
import fr.dynamx.common.physics.utils.SynchronizedRigidBodyTransform;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.utils.physics.PhysicsShapes;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = Main.MODID)
public class ClothEntityModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, AttachedBodySynchronizer {

    public final PhysicsEntity<?> entity;
    private final Map<Byte, PhysicsRigidBody> attachedParts = new HashMap();
    private final HashMap<Byte, SynchronizedRigidBodyTransform> attachedBodiesTransform = new HashMap();
    @SynchronizedEntityVariable(
            name = "part_states"
    )
    private final EntityTransformsVariable synchronizedTransforms;
    public PhysicsSoftBody cloth = null;

    public ClothEntityModule(PhysicsEntity<?> entity) {
        this.entity = entity;
        this.synchronizedTransforms = new EntityTransformsVariable(entity, this);
        System.out.println("WarningSignEntityModule created");
    }


    @Override
    public void onRemovedFromWorld() {
        if (cloth != null) {
            DynamXContext.getPhysicsWorld(entity.world).removeCollisionObject(cloth);
            cloth = null;
        }
    }

    @Override
    public void initPhysicsEntity(@Nullable AbstractEntityPhysicsHandler<?, ?> handler) {
        if(!entity.world.isRemote) return;
        System.out.println("initPhysicsEntity called");


        PhysicsSoftSpace dynamicsWorld = DynamXContext.getPhysicsWorld(entity.world).getDynamicsWorld();

        IndexedMesh clothGrid = PhysicsShapes.createClothGrid(9, 9, 0.5f);
        cloth = new PhysicsSoftBody();

        NativeSoftBodyUtil.appendFromNativeMesh(clothGrid, cloth);
        cloth.setMargin(0.10f);
        cloth.setMass(50);

        SoftBodyConfig config = cloth.getSoftConfig();
        config.set(Sbcp.Damping, 0.01f);
        config.set(Sbcp.Drag, 0.5f);
        config.set(Sbcp.Lift, 10f);
        config.setAerodynamics(Aero.F_TwoSidedLiftDrag);
        config.setPositionIterations(10);

        SoftBodyMaterial softMaterial = cloth.getSoftMaterial();
        softMaterial.setAngularStiffness(0f);

        Quaternion rotation = new Quaternion();
        rotation.fromAngles(FastMath.HALF_PI, 0f, 0f);
        cloth.generateClusters();

        cloth.setPhysicsLocation(DynamXUtils.toVector3f(entity.getPosition()));


        dynamicsWorld.addCollisionObject(cloth);






        cloth.applyTranslation(new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ));
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


