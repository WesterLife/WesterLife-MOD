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
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.3D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
        GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);



        DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());

        if(!te.isLinked()) {
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("linke", (byte) te.getBlockMetadata());
        }

        GlStateManager.popMatrix();
    }
}
