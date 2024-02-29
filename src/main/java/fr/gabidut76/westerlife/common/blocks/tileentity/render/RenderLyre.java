package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileLyre;
import net.minecraft.client.renderer.GlStateManager;

public class RenderLyre extends TESRDynamXBlock<TileLyre> {


    @Override
    public void render(TileLyre te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        if(te.isFlip()) {
            GlStateManager.rotate(180, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0, -1.0f, 0);
        }
        if(te.isBlink()) {
            // apply glowing effect arround the lyre
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            // inverse color
            GlStateManager.colorMask(true, false, true, true);
        }
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("base", (byte) te.getBlockMetadata(), false);
        if(te.isBlink()) {
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
        GlStateManager.rotate(te.getActualrotation().x, 0.0F, 1.0F, 0.0F);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("yaw", (byte) te.getBlockMetadata(), false);
        GlStateManager.translate(0,1.094f,0);
        GlStateManager.rotate(te.getActualrotation().y, 0.0F, 0.0F,1.0F);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("pitch", (byte) te.getBlockMetadata(), false);
        GlStateManager.popMatrix();
    }


}
