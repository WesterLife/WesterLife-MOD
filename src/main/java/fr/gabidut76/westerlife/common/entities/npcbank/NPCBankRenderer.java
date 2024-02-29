package fr.gabidut76.westerlife.common.entities.npcbank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static org.lwjgl.opengl.GL11.GL_QUADS;

@SideOnly(Side.CLIENT)
public class NPCBankRenderer extends RenderBiped<NPCBank> {


    public NPCBankRenderer(RenderManager renderManager) {
        super(renderManager, new ModelPlayer(0f,false), 1f);
    }

    @Override
    public void doRender(NPCBank entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NPCBank entity) {
        return entity.getTexture();
    }


    @Override
    protected boolean canRenderName(NPCBank entity) {
        return false;
    }
}
