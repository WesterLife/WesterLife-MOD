package fr.gabidut76.westerlife.common.entities.npcdomac;

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
public class NPCDomacEntityRenderer extends RenderBiped<NPCDomacEntity> {


    public NPCDomacEntityRenderer(RenderManager renderManager) {
        super(renderManager, new ModelPlayer(0f,false), 1f);
    }

    @Override
    public void doRender(NPCDomacEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.pushMatrix();


        GlStateManager.translate(x, y + 2.2f, z);
        GlStateManager.scale(.3f, .3f, .3f);
        if(entity.getStatusData().equals("waiting")) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("westerlife:textures/entities/actionsbubbles/waiting.png"));
        } else if (entity.getStatusData().equals("ok")) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("westerlife:textures/entities/actionsbubbles/ok.png"));
        }
        GlStateManager.rotate(Minecraft.getMinecraft().player.rotationYaw, 0, -1, 0);
        GlStateManager.glBegin(GL_QUADS);
        GlStateManager.glTexCoord2f(0, 0);
        GlStateManager.glVertex3f(-.5f, 0, 0);
        GlStateManager.glTexCoord2f(0, -1);
        GlStateManager.glVertex3f(-.5f, 1, 0);
        GlStateManager.glTexCoord2f(-1, -1);
        GlStateManager.glVertex3f(.5f, 1, 0);
        GlStateManager.glTexCoord2f(-1, 0);
        GlStateManager.glVertex3f(.5f, 0, 0);
        GlStateManager.glEnd();




        GlStateManager.popMatrix();
    }

    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {

    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NPCDomacEntity entity) {
        return entity.getTexture();
    }


    @Override
    protected boolean canRenderName(NPCDomacEntity entity) {
        return false;
    }
}
