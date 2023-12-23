package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.blocks.TEDynamXBlock;
import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import net.minecraft.client.renderer.GlStateManager;

public class RenderTileMovingGate extends TESRDynamXBlock<TileMovingGate> {
    private static int ro = 0;

    @Override
    public void render(TileMovingGate te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getPackInfo() != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y - 0.835, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata(), false);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y-0.476, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(te.getA(), -1.0F, 0.0F, 0);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("move", (byte) te.getBlockMetadata(), false);

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            GlStateManager.popMatrix();


        }
    }
}
