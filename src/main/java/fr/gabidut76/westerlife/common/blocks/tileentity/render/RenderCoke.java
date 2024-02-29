package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCoke;
import net.minecraft.client.renderer.GlStateManager;

public class RenderCoke extends TESRDynamXBlock<TileCoke> {


    @Override
    public void render(TileCoke te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

        if(getWorld().getTotalWorldTime() - te.placedAt > (20 * 60 * 2)) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("baby", (byte) te.getBlockMetadata(), false);
        }

        if(getWorld().getTotalWorldTime() - te.placedAt > (20 * 60 * 4)) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("plantation", (byte) te.getBlockMetadata(), false);
        }
        if(getWorld().getTotalWorldTime() - te.placedAt > (20 * 60 * 6)) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("leaves", (byte) te.getBlockMetadata(), false);
        }

        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("pot", (byte) te.getBlockMetadata(), false);
        GlStateManager.popMatrix();
    }


}
