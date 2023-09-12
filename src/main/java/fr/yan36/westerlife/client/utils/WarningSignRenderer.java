package fr.yan36.westerlife.client.utils;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.PropsEntity;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.dynamx.utils.physics.DynamXPhysicsHelper;
import fr.yan36.westerlife.Main;

import fr.yan36.westerlife.common.entities.DynamX.punchingball.TestEntityModule2;
import fr.yan36.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.yan36.westerlife.common.entities.DynamX.warningsign.WarningSignEntityModule;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class WarningSignRenderer extends RenderPhysicsEntity<WarningSignEntity> {

    public WarningSignRenderer(RenderManager manager) {
        super(manager);
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.InitRenderer<>(PropsEntity.class, this));
    }

    public void renderMain(WarningSignEntity entity, float partialsTicks) {
        DynamXContext.getObjModelRegistry().getModel(new ResourceLocation(Main.MODID, "models/dynamx/blocks/highroad/warningsign/warningsign.obj")).renderGroups("base", (byte) 0);


        if(entity.getModuleByType(WarningSignEntityModule.class) != null) {
            WarningSignEntityModule module = entity.getModuleByType(WarningSignEntityModule.class);
            glPushMatrix();


            Vector3f physicsLocation = module.punchingBag.getPhysicsLocation(null);
            Quaternion q2 = module.punchingBag.getPhysicsRotation(null);


            glTranslatef(physicsLocation.x, physicsLocation.y,physicsLocation.z);
            glTranslatef(-(float) entity.posX, -(float) entity.posY, -(float) entity.posZ);
            org.lwjgl.util.vector.Quaternion q = new org.lwjgl.util.vector.Quaternion(q2.getX(), q2.getY(), q2.getZ(), q2.getW());

//            System.out.println(q2.getX() + " " + q2.getY() + " " + q2.getZ() + " " + q2.getW());
            GlStateManager.rotate(q);
            GlStateManager.translate(0,-1.1,0);
            DynamXContext.getObjModelRegistry().getModel(new ResourceLocation(Main.MODID, "models/dynamx/blocks/highroad/warningsign/warningsign.obj")).renderGroups("moving", (byte) 0);
            glPopMatrix();

        }




        this.renderParts(entity, partialsTicks);
    }




    public void renderParts(WarningSignEntity entity, float partialTicks) {

    }

    @Override
    public boolean shouldRender(WarningSignEntity livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }


}

