package fr.yan36.westerlife.client.renderer;

import com.jme3.bullet.objects.PhysicsRigidBody;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.utils.debug.renderer.PhysicsDebugRenderer;
import fr.dynamx.utils.optimization.GlQuaternionPool;
import fr.dynamx.utils.optimization.QuaternionPool;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.yan36.westerlife.common.utils.physics.RendererHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

import static fr.dynamx.client.handlers.ClientDebugSystem.getCurrentRigidBodyTransform;
import static fr.dynamx.client.handlers.ClientDebugSystem.getPrevRigidBodyTransform;

@Mod.EventBusSubscriber
public class DebugRenderer {
    /*@SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent e) {
        Minecraft MC = Minecraft.getMinecraft();
        Vector3fPool.openPool();
        QuaternionPool.openPool();
        GlQuaternionPool.openPool();
        Iterator var8 = DynamXContext.getPhysicsWorld(MC.world).getDynamicsWorld().getRigidBodyList().iterator();

        while(var8.hasNext()) {
            PhysicsRigidBody body = (PhysicsRigidBody)var8.next();
            PhysicsDebugRenderer.debugRigidBody(body, getPrevRigidBodyTransform(body.nativeId()), getCurrentRigidBodyTransform(body.nativeId()), e.getPartialTicks());
        }


        Vector3fPool.closePool();
        QuaternionPool.closePool();
    }*/
}
