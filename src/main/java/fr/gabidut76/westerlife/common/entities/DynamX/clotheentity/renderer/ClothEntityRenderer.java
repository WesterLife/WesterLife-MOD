package fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.renderer;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.client.renders.scene.EntityRenderContext;
import fr.dynamx.common.entities.PropsEntity;
import fr.dynamx.utils.optimization.GlQuaternionPool;
import fr.dynamx.utils.optimization.QuaternionPool;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.ClothEntity;
import fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.ClothEntityModule;
import fr.gabidut76.westerlife.common.utils.physics.RendererHelper;
import jme3utilities.math.MyBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import javax.annotation.Nullable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ClothEntityRenderer extends RenderPhysicsEntity<ClothEntity> {

    public ClothEntityRenderer(RenderManager manager) {
        super(manager);
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.InitRenderer<>(PropsEntity.class, this));
    }

    @Override
    public void renderEntity(ClothEntity entity, EntityRenderContext entityRenderContext) {
        ClothEntityModule module = entity.getModuleByType(ClothEntityModule.class);

        if (entity.getModuleByType(ClothEntityModule.class) != null) {


            GL11.glPushMatrix(); // Code du repo de DynamX sur la branch softbodies
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableCull();

            Vector3fPool.openPool();
            QuaternionPool.openPool();
            GlQuaternionPool.openPool();

            Vector3f physicsLocation = Vector3fPool.get();
            Quaternion physicsRotation = QuaternionPool.get();
            module.cloth.getPhysicsLocation(physicsLocation);
            module.cloth.getPhysicsRotation(physicsRotation);

            IntBuffer faces = module.cloth.copyFaces(null);
            FloatBuffer nodeLocations = module.cloth.copyLocations(null);
            int numFaces = module.cloth.countFaces();

            Color col = new Color(0, 155, 0);

            for (int i = 0; i < numFaces; i++) {
                int vi1 = faces.get(3 * i);
                int vi2 = faces.get(3 * i + 1);
                int vi3 = faces.get(3 * i + 2);
                Vector3f nodePos1 = new Vector3f();
                Vector3f nodePos2 = new Vector3f();
                Vector3f nodePos3 = new Vector3f();
                MyBuffer.get(nodeLocations, 3 * vi1, nodePos1);
                MyBuffer.get(nodeLocations, 3 * vi2, nodePos2);
                MyBuffer.get(nodeLocations, 3 * vi3, nodePos3);

                Vector3f[] x = new Vector3f[]{nodePos1, nodePos2, nodePos3};
                GlStateManager.translate(-physicsLocation.x, -physicsLocation.y, -physicsLocation.z);
                RendererHelper.drawTriangle(
                        x[0],
                        x[1],
                        x[2],
                        col);
                GlStateManager.translate(physicsLocation.x, physicsLocation.y, physicsLocation.z);

            }

            Vector3fPool.closePool();
            QuaternionPool.closePool();
            GlQuaternionPool.closePool();

            GlStateManager.enableTexture2D();

            GlStateManager.enableCull();
            GlStateManager.enableDepth();
            GL11.glPopMatrix();


        }
    }

    @Override
    public void renderEntityDebug(ClothEntity clothEntity, EntityRenderContext entityRenderContext) {

    }

    @Nullable
    @Override
    public EntityRenderContext getRenderContext(ClothEntity clothEntity) {
        return new EntityRenderContext(new ClothEntityRenderer(this.renderManager));
    }


    public void renderParts(ClothEntity entity, float partialTicks) {

    }

    @Override
    public boolean shouldRender(ClothEntity livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }


}

