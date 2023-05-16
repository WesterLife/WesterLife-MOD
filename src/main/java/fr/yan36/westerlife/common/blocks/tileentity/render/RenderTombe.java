package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.yan36.westerlife.common.blocks.tileentity.TileTombe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class RenderTombe extends TESRDynamXBlock<TileTombe> {
    private static int ro = 0;

    @Override
    public void render(TileTombe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getBlockObjectInfo() != null) {


            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.3D + (te.getBlockObjectInfo().getTranslation()).y-0.476, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
            GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("cube", (byte) te.getBlockMetadata());



            GlStateManager.pushMatrix();
            GlStateManager.translate(0.2,0.15,0.737);
//            GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
            GlStateManager.scale(0.01,-0.01,0.01);
//            GlStateManager.translate(0,0.5d,0.5f);
//            GlStateManager.rotate(te.getRotation() * 22.5f, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(180f, 0.0F, -1.0F, 0.0F);
            GlStateManager.color(0.5f,0.5f,0.5f);
            Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, te.getText(), 0,0, 50, new Color(50,50,50).getRGB());
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();




        }
    }

}
