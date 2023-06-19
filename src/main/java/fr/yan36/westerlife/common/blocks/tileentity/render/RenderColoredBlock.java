package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.blocks.tileentity.TileChair;
import fr.yan36.westerlife.common.blocks.tileentity.TileColoredBlock;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderColoredBlock extends TESRDynamXBlock<TileColoredBlock> {
    private static int ro = 0;

    @Override
    public void render(TileColoredBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getBlockObjectInfo() != null) {



            int r = (te.getColor() >> 16) & 0xFF - 50;
            int g = (te.getColor() >> 8) & 0xFF - 50;
            int b = te.getColor() & 0xFF - 50;



            GlStateManager.pushMatrix();

            GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.51D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
            GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());

            GL11.glColor4f(r / 255F, g / 255F, b / 255F, 1F);

            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("colored", (byte) te.getBlockMetadata());
            GL11.glColor3f(1, 1, 1);
            GlStateManager.popMatrix();









        }
    }

}
