package fr.gabidut76.westerlife.client.utils;

import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.client.renders.scene.EntityRenderContext;
import fr.dynamx.common.entities.PropsEntity;
import fr.gabidut76.westerlife.common.entities.DynamX.punchingball.TestEntity2;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;

public class TestEntity2Renderer extends RenderPhysicsEntity<TestEntity2> {

    public TestEntity2Renderer(RenderManager manager) {
        super(manager);
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.InitRenderer<>(PropsEntity.class, this));
    }

    @Override
    public void renderEntity(TestEntity2 testEntity2, EntityRenderContext entityRenderContext) {

    }

    @Override
    public void renderEntityDebug(TestEntity2 testEntity2, EntityRenderContext entityRenderContext) {

    }

    @Nullable
    @Override
    public EntityRenderContext getRenderContext(TestEntity2 testEntity2) {
        return null;
    }


    public void renderParts(TestEntity2 entity, float partialTicks) {

    }

    @Override
    public boolean shouldRender(TestEntity2 livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }


}

