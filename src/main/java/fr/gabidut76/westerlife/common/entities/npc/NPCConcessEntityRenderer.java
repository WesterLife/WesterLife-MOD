package fr.gabidut76.westerlife.common.entities.npc;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class NPCConcessEntityRenderer extends RenderBiped<NPCConcessEntity> {


    public NPCConcessEntityRenderer(RenderManager renderManager) {
        super(renderManager, new ModelPlayer(0f,false), 1f);
    }

    @Override
    public void doRender(NPCConcessEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NPCConcessEntity entity) {
        return new ResourceLocation("westerlife:textures/entities/default.png");
    }


    @Override
    protected boolean canRenderName(NPCConcessEntity entity) {
        return false;
    }
}
