package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.blocks.tileentity.TileGarage;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderGarage extends TESRDynamXBlock<TileGarage> {


    @Override
    public void render(TileGarage te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);



        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata(), false);

        if(!te.isLinked()) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("linke", (byte) te.getBlockMetadata(), false);
        }

        GlStateManager.popMatrix();
    }
}
