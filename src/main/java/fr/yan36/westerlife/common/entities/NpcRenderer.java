package fr.yan36.westerlife.common.entities;

import fr.yan36.westerlife.common.entities.DynamX.TestEntity2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
public class NpcRenderer extends RenderLiving<NPCTestEntity> {

    public NpcRenderer(ModelBase m, float v) {
        super(Minecraft.getMinecraft().getRenderManager(), m, v);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NPCTestEntity entity) {
        return new ResourceLocation("westerlife:textures/entities/test.png");
    }

    @Override
    public void doRender(NPCTestEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }


    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }

    @Override
    protected boolean canRenderName(NPCTestEntity entity) {
        return false;
    }
}
