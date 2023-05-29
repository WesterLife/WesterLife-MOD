package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.handlers.ClientDebugSystem;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.blocks.TEDynamXBlock;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileFeuRouge;
import fr.yan36.westerlife.common.blocks.tileentity.TileRadarFixe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

import static net.minecraft.client.renderer.GlStateManager.popMatrix;
import static net.minecraft.client.renderer.GlStateManager.pushMatrix;

public class RenderFeuRouge extends TESRDynamXBlock<TileFeuRouge> {


    @Override
    public void render(TileFeuRouge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.3D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
        GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        if(te.getPosition() == 1) {
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("bas", (byte) te.getBlockMetadata());
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("poteau1", (byte) te.getBlockMetadata());
        } else {
            GlStateManager.translate(0,-1,0);
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("haut", (byte) te.getBlockMetadata());
            DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("poteau2", (byte) te.getBlockMetadata());
        }


        if(te.getSyncvalue() == -160*2 || te.getSyncvalue() == -320*2) {
            if (getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) >= 0 && getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) <= 60*2) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("red" + te.getPosition(), (byte) te.getBlockMetadata());
            } else if((getWorld().getWorldTime() % te.getSyncvalue() >= 290*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 320*2)) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("red" + te.getPosition(), (byte) te.getBlockMetadata());
            } else if(getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) >= 50*2 && getWorld().getWorldTime() % Math.abs(te.getSyncvalue()) <= 90*2) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("orange" + te.getPosition(), (byte) te.getBlockMetadata());
            } else {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("green" + te.getPosition(), (byte) te.getBlockMetadata());
            }
        } else {
            if (getWorld().getWorldTime() % te.getSyncvalue() >= 0 && getWorld().getWorldTime() % te.getSyncvalue() <= 60*2) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("green" + te.getPosition(), (byte) te.getBlockMetadata());
            } else if((getWorld().getWorldTime() % te.getSyncvalue() >= 290*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 320*2)) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("orange" + te.getPosition(), (byte) te.getBlockMetadata());
            } else if(((getWorld().getWorldTime() % te.getSyncvalue() >= 50*2 && getWorld().getWorldTime() % te.getSyncvalue() <= 80*2))) {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("red" + te.getPosition(), (byte) te.getBlockMetadata());
            } else {
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("red" + te.getPosition(), (byte) te.getBlockMetadata());
            }
        }
        GlStateManager.popMatrix();
    }
}
