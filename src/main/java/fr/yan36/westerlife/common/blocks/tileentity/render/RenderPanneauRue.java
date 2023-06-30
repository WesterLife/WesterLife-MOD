package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauAgglomeration;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauRue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderPanneauRue extends TESRDynamXBlock<TilePanneauRue> {

    @Override
    public void render(TilePanneauRue te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.2D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
        GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(200f, 0.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0, 0.80f, 0.07f);
        GlStateManager.scale(0.007F, 0.007F, 0.007F);

        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, te.getName(), 0, 0, 80, Color.BLACK.getRGB());
        GL11.glTranslatef(0, 8f, 0);
        GlStateManager.scale(.5F, .5F, .5F);
        GlStateManager.popMatrix();
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }
}
