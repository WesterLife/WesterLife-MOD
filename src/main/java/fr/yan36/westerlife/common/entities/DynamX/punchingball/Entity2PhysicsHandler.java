package fr.yan36.westerlife.common.entities.DynamX.punchingball;


import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.joints.Point2PointJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.common.physics.entities.EntityPhysicsHandler;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;

public class Entity2PhysicsHandler extends EntityPhysicsHandler<TestEntity2> {


    public Entity2PhysicsHandler(TestEntity2 entity) {
        super(entity);
    }

    @Override
    protected PhysicsRigidBody createShape(Vector3f vector3f, Quaternion quaternion, float v) {



        CollisionShape shape = new BoxCollisionShape(0.5f, 0.5f, 0.5f);
        PhysicsRigidBody physA = DynamXPhysicsHelper.fastCreateRigidBody(handledEntity, 10f, shape, vector3f, 0);
        PhysicsRigidBody physB = DynamXPhysicsHelper.fastCreateRigidBody(handledEntity, 10f, shape, vector3f.add(0,2,0), 0);

        Point2PointJoint joint = new Point2PointJoint(physA, physB, Vector3fPool.get(0,0,0), Vector3fPool.get(0,2,0));

        physA.addJoint(joint);



        return physA;
    }

}
