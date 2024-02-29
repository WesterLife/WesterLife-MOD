package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileFeuRouge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class RenderFeuRouge extends TESRDynamXBlock<TileFeuRouge> {


    @Override
    public void render(TileFeuRouge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        if(te.getPosition() == 1) {
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("bas", (byte) te.getBlockMetadata(), false);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("poteau1", (byte) te.getBlockMetadata(), false);
        } else if(te.getPosition() == 3) {
            GlStateManager.translate(0,-1,0);
            Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, "CPT", 0,0,0,0xFFFFFF);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("pieton", (byte) te.getBlockMetadata(), false);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("poteau2", (byte) te.getBlockMetadata(), false);
        } else {
            GlStateManager.translate(0,-1,0);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("haut", (byte) te.getBlockMetadata(), false);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("poteau2", (byte) te.getBlockMetadata(), false);
        }


        if(te.getSyncvalue() == -160*2 || te.getSyncvalue() == -320*2) {
            if (getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) >= 0 && getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) <= 60*2) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("red" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else if((getWorld().getWorldTime() % te.getSyncvalue() >= 290*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 320*2)) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("red" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else if(getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) >= 50*2 && getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) <= 90*2) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("orange" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("green" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            }
        } else {
            if (getWorld().getWorldTime() % te.getSyncvalue() >= 0 && getWorld().getWorldTime() % te.getSyncvalue() <= 60*2) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("green" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else if((getWorld().getWorldTime() % te.getSyncvalue() >= 290*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 320*2)) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("orange" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else if(((getWorld().getWorldTime() % te.getSyncvalue() >= 50*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 80*2))) {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("red" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            } else {
                DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("red" + te.getPosition(), (byte) te.getBlockMetadata(), false);
            }
        }
        GlStateManager.popMatrix();
    }
}
