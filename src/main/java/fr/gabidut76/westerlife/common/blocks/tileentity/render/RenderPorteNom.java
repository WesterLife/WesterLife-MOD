package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TilePorteNom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderPorteNom extends TESRDynamXBlock<TilePorteNom> {


    @Override
    public void render(TilePorteNom te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.2D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        GlStateManager.rotate(200f, -1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0, 0.95f, 0.28f);

        GlStateManager.scale(0.01F, 0.01F, 0.01F);

        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, te.getName(), 0, 0, 150, 0xFFFFFF);
        GL11.glTranslatef(0, 8f, 0.188f);
        GlStateManager.scale(.5F, .5F, .5F);
        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, te.getFunction(), 0, 0, 200, 0xFFFFFF);
        GlStateManager.popMatrix();
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }
}
