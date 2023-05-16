package fr.yan36.westerlife.client.utils;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.PropsEntity;
import fr.dynamx.common.physics.joints.EntityJoint;
import fr.dynamx.utils.client.ClientDynamXUtils;
import fr.dynamx.utils.maths.DynamXMath;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.TestEntity2;
import fr.yan36.westerlife.common.entities.TestEntityModule2;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class TestEntity2Renderer extends RenderPhysicsEntity<TestEntity2> {

    public TestEntity2Renderer(RenderManager manager) {
        super(manager);
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.InitRenderer<>(PropsEntity.class, this));
    }

    public void renderMain(TestEntity2 entity, float partialsTicks) {
        DynamXContext.getObjModelRegistry().getModel(new ResourceLocation(Main.MODID, "punch.obj")).renderGroups("base", (byte) 0);


        if(entity.getModuleByType(TestEntityModule2.class) != null) {
            TestEntityModule2 module = entity.getModuleByType(TestEntityModule2.class);
            glPushMatrix();
//            glTranslatef(0, -2.3f, 0);

            // jm3 quat to ljw quat
            Quaternion q2 = module.punchingBag.getPhysicsRotation(null);
            org.lwjgl.util.vector.Quaternion q = new org.lwjgl.util.vector.Quaternion(q2.getX(), q2.getY(), q2.getZ(), q2.getW());
            // set pivot point to top of punching bag

            GlStateManager.rotate(q);
            DynamXContext.getObjModelRegistry().getModel(new ResourceLocation(Main.MODID, "punch.obj")).renderGroups("part", (byte) 0);
            glPopMatrix();

        }




        this.renderParts(entity, partialsTicks);
    }




    public void renderParts(TestEntity2 entity, float partialTicks) {

    }

    @Override
    public boolean shouldRender(TestEntity2 livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }


}

