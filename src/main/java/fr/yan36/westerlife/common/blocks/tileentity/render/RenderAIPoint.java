package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.dynamx.BlockAIPoint;
import fr.yan36.westerlife.common.blocks.tileentity.TileAIPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.List;


public class RenderAIPoint extends TESRDynamXBlock<TileAIPoint> {

    @Override
    public void render(TileAIPoint te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 0.5D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
        GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        if (te.getType().equals(BlockAIPoint.Type.GO)) {
            int k = te.getPos().getX() % 255;
            GL11.glColor4f(0.0F, k, 0.0F, 0.5F);
        } else if (te.getType().equals(BlockAIPoint.Type.STOP)) {
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);

        }

        GlStateManager.translate(0, Math.cos(Minecraft.getMinecraft().world.getTotalWorldTime() / 10.0) / 2, 0);

        DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Cylinder", (byte) te.getBlockMetadata());
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 0.5D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);

        BlockPos nearest = Util.getNearestBlockAIPoint(te.getWorld(), te.getPos(), 10);

        if (!nearest.equals(new BlockPos(-1, -1, -1))) {

            GL11.glLineWidth(2);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3d(0, 0, 0);



            GL11.glVertex3d((nearest.getX() - te.getPos().getX()), (nearest.getY() - te.getPos().getY()), (nearest.getZ() - te.getPos().getZ()));




            GL11.glEnd();

        }

        GlStateManager.popMatrix();

    }
}
