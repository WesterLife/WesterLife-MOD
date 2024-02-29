package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.blocks.tileentity.TilePoteauLevant;
import net.minecraft.client.renderer.GlStateManager;

public class RenderTilePoteuLevant extends TESRDynamXBlock<TilePoteauLevant> {
    private static int ro = 0;

    @Override
    public void render(TilePoteauLevant te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getPackInfo() != null) {
            GlStateManager.pushMatrix();

            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.4D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("base", (byte) te.getBlockMetadata(), false);

            GlStateManager.translate(0,(float) te.getA() / 100,0);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("moving", (byte) te.getBlockMetadata(), false);

            GlStateManager.popMatrix();


        }
    }
}
