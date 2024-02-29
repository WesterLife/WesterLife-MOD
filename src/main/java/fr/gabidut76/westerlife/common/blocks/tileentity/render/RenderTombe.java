package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileTombe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class RenderTombe extends TESRDynamXBlock<TileTombe> {
    private static int ro = 0;

    @Override
    public void render(TileTombe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te.getPackInfo() != null) {


            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y-0.476, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("cube", (byte) te.getBlockMetadata(), false);



            GlStateManager.pushMatrix();
            GlStateManager.translate(0.2,0.15,0.737);
            GlStateManager.scale(0.01,-0.01,0.01);
            GlStateManager.rotate(180f, 0.0F, -1.0F, 0.0F);
            GlStateManager.color(0.5f,0.5f,0.5f);
            Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, te.getText(), 0,0, 50, new Color(50,50,50).getRGB());
            GlStateManager.popMatrix();

            GlStateManager.popMatrix();




        }
    }

}
