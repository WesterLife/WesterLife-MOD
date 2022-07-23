package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("ALL")
public class TEBisignRender extends TESRDynamXBlock<TEBisign> {

    @Override
    public void render(TEBisign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        String toRender = "";
        if(te.getTileData().getInteger("state") == 0) {
            toRender = "none";
        } else if (te.getTileData().getInteger("state") == 1) {
            toRender = "greenpane";
        } else if (te.getTileData().getInteger("state") == 2) {
            toRender = "redpane";
        } else toRender = "none";

        if(!toRender.equals("none")) {
            //(System.out.println(toRender);
            GL11.glPushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180, 0F, 0F, 1F);
            GL11.glRotatef( te.getRotation() * 22.5F, 1.0F, 0.0F, 0.0F);
            GL11.glPushMatrix();
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("greenpane", (byte) te.getBlockMetadata());
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            GL11.glPushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
            GL11.glRotatef(180, 0F, 0F, 1F);
            GL11.glRotatef( te.getRotation() * 22.5F, 1.0F, 0.0F, 0.0F);
            GL11.glPushMatrix();
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("redpane", (byte) te.getBlockMetadata());
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }


        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);
        GL11.glRotatef( te.getRotation() * 22.5F, 0.0F, 0.0F, 1.0F);
        GL11.glPushMatrix();
        DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}
