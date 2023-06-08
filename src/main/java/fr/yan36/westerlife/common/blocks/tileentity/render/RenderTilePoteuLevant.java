package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.yan36.westerlife.common.blocks.tileentity.TilePoteauLevant;
import net.minecraft.client.renderer.GlStateManager;

public class RenderTilePoteuLevant extends TESRDynamXBlock<TilePoteauLevant> {
    private static int ro = 0;

    @Override
    public void render(TilePoteauLevant te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getBlockObjectInfo() != null) {
            GlStateManager.pushMatrix();

            GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.4D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
            GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());

            GlStateManager.translate(0,(float) te.getA() / 100,0);

            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("moving", (byte) te.getBlockMetadata());

            GlStateManager.popMatrix();


        }
    }
}
