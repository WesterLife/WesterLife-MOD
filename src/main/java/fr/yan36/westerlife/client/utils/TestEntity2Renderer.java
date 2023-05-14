package fr.yan36.westerlife.client.utils;

import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.PropsEntity;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.TestEntity2;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class TestEntity2Renderer extends RenderPhysicsEntity<TestEntity2> {

    public TestEntity2Renderer(RenderManager manager) {
        super(manager);
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.InitRenderer<>(PropsEntity.class, this));
    }

    public void renderMain(TestEntity2 entity, float partialsTicks) {
        this.renderModel(DynamXContext.getObjModelRegistry().getModel(new ResourceLocation(Main.MODID, "test.obj")), entity, (byte)0);
        this.renderParts(entity, partialsTicks);
    }




    public void renderParts(TestEntity2 entity, float partialTicks) {

    }

    @Override
    public boolean shouldRender(TestEntity2 livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }


}

