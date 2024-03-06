package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.aym.acsguis.cssengine.font.CssFontHelper;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCoke;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCokeTable;
import fr.gabidut76.westerlife.common.objects.RenderTileBinded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;

@RenderTileBinded(tileEntityBinded = TileCokeTable.class)
public class RenderCokeTable extends TESRDynamXBlock<TileCokeTable> {


    @Override
    public void render(TileCokeTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("table", (byte) te.getBlockMetadata(), false);

        if(te.placedLeavesInPot < 6) {
            for (int i = 1; i < te.placedLeavesInPot; i++) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("leaf" + i, (byte) te.getBlockMetadata(), false);
            }
        } else {
            int waitedtime = (int) (getWorld().getTotalWorldTime() - te.placedAt);

            if(waitedtime > 100f) GL11.glColor3f(0.7f,0.7f,0);
            if(waitedtime > 200f) GL11.glColor3f(0.5f,0.4f,0);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("resine", (byte) te.getBlockMetadata(), false);
            GL11.glColor3f(1,1,1);
        }


        if(te.isResinReady) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("resinejoint", (byte) te.getBlockMetadata(), false);
        }

        GlStateManager.pushMatrix();

        GlStateManager.scale(0.01, -0.01, 0.01);

        Minecraft.getMinecraft().fontRenderer.drawString("Placed leaves: " + te.placedLeavesInPot, 0, 0, Color.WHITE.getRGB());

        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }


}
