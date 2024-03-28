package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileLightController;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileSpot;
import fr.gabidut76.westerlife.common.objects.RenderTileBinded;
import net.minecraft.client.renderer.GlStateManager;

@RenderTileBinded(tileEntityBinded = TileLightController.class)
public class RenderLightController extends TESRDynamXBlock<TileLightController> {
    @Override
    public void render(TileLightController te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("base", (byte) te.getBlockMetadata(), false);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("light", (byte) te.getBlockMetadata(), false);
        GlStateManager.popMatrix();
    }
}
