package fr.gabidut76.westerlife.common.entities.DynamX.clotheentity;

import com.jme3.bullet.objects.PhysicsSoftBody;
import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.AttachedBodySynchronizer;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.PhysicsEntity;
import fr.dynamx.common.network.sync.variables.EntityTransformsVariable;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.dynamx.common.physics.utils.RigidBodyTransform;
import fr.dynamx.common.physics.utils.SynchronizedRigidBodyTransform;
import fr.gabidut76.westerlife.westercore.Main;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = Main.MODID)
public class ClothEntityModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, AttachedBodySynchronizer {

    public final PhysicsEntity<?> entity;
    private final Map<Byte, PhysicsSoftBody> attachedParts = new HashMap();
    private final HashMap<Byte, SynchronizedRigidBodyTransform> attachedBodiesTransform = new HashMap();
    @SynchronizedEntityVariable(
            name = "part_states"
    )
    private final EntityTransformsVariable synchronizedTransforms;
    public PhysicsSoftBody cloth = null;

    public ClothEntityModule(PhysicsEntity<?> entity) {
        this.entity = entity;
        this.synchronizedTransforms = new EntityTransformsVariable(entity, this);
        System.out.println("ClothModule created");

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
        }
    }
}


