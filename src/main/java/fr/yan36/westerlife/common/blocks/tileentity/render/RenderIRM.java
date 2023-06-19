package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileColoredBlock;
import fr.yan36.westerlife.common.blocks.tileentity.TileIrm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderIRM extends TESRDynamXBlock<TileIrm> {
    private static int ro = 0;

    @Override
    public void render(TileIrm te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getBlockObjectInfo() != null) {


            GlStateManager.pushMatrix();

            GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.51D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
            GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.909f, 0.45F, 1.19f);
            GL11.glScalef(0.003f, 0.003f, 0.003f);
            GL11.glRotatef(180, -1, 0, 0);
            GL11.glRotatef(90, 0, -1, 0);
            if(!te.isRunning() && !te.isRollingBack()) {
                Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, "Prêt.", 0,0, 100, 0x101010);
            } else if (te.isRunning() && !te.isRollingBack()) {
                Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, "En marche.", 0,0, 100, 0x101010);
            } else if (!te.isRunning() && te.isRollingBack()) {
                Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, "En retour.", 0,0, 100, 0x101010);
            }
            GL11.glPopMatrix();
            GL11.glTranslatef(-(te.getStep() / 1000f), 0F, 0f);

            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("bed", (byte) te.getBlockMetadata());
            GL11.glColor3f(1, 1, 1);
            GlStateManager.popMatrix();









        }
    }

}
