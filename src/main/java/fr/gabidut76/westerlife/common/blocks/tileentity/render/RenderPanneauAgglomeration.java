package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TilePanneauAgglomeration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderPanneauAgglomeration extends TESRDynamXBlock<TilePanneauAgglomeration> {


    @Override
    public void render(TilePanneauAgglomeration te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.2D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(200f, 0.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0, 0.90f, 0.080f);
        GlStateManager.scale(0.01F, 0.01F, 0.01F);

        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, te.getName(), 0, 0, 90, Color.BLACK.getRGB());
        GL11.glTranslatef(0, 8f, 0);
        GlStateManager.scale(.5F, .5F, .5F);
        GlStateManager.popMatrix();
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }
}
