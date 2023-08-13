package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileMacdo;
import fr.yan36.westerlife.common.blocks.tileentity.TilePanneauAgglomeration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;

public class RenderMacdo extends TESRDynamXBlock<TileMacdo> {


    @Override
    public void render(TileMacdo te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        int[] randomTable = new int[]{250,126,59,245,36,40,245,204,152,320,250,126,59,245,36,40,245,204,152,320,250,126,59,245,36,40,245,204,152,320,250,126,59,245,36,40,245,204,152,320};
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getBlockObjectInfo().getTranslation()).x, y + 1.5D + (te.getBlockObjectInfo().getTranslation()).y, z + 0.5D + (te.getBlockObjectInfo().getTranslation()).z);
        GlStateManager.scale((te.getBlockObjectInfo().getScaleModifier()).x, (te.getBlockObjectInfo().getScaleModifier()).y, (te.getBlockObjectInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata());
        GlStateManager.pushMatrix();
        if(te.getSteakstate() >= 1 || te.getSteakstate() <= 6) {
            if(te.getSteakstate() >= 1) {
                GlStateManager.translate(0, -0.51f, 2f);
                GL11.glColor3f(0.5f, 0.5f, 0.5f);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GL11.glColor3f(1f, 1f, 1f);
                GlStateManager.translate(0, 0.51f, -2f);
            }
            if(te.getSteakstate() >= 2) {
                GlStateManager.translate(0.2f, -0.51f, 1.6f);
                GlStateManager.rotate(45, 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(45, 0, -1, 0);
                GlStateManager.translate(-0.2f, 0.51f, -1.6f);
            }
            if(te.getSteakstate() >= 3) {
                GlStateManager.translate(-0.15f, -0.51f, 1.6f);
                GlStateManager.rotate(-45, 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(-45, 0, -1, 0);
                GlStateManager.translate(0.15f, 0.51f, -1.6f);
            }
            if(te.getSteakstate() >= 4) {
                GlStateManager.translate(0.09f, -0.51f, 1.2f);
                GlStateManager.rotate(28, 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(-28, 0, -1, 0);
                GlStateManager.translate(-0.09f, 0.51f, -1.2f);
            }
            if(te.getSteakstate() >= 5) {
                GlStateManager.translate(0.14f, -0.51f, 0.9f);
                GlStateManager.rotate(67, 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(-67, 0, -1, 0);
                GlStateManager.translate(-0.14f, 0.51f, -0.9f);
            }
            if(te.getSteakstate() >= 6) {
                GlStateManager.translate(0.0f, -0.51f, 1.1f);
                GlStateManager.rotate(78, 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(-78, 0, -1, 0);
                GlStateManager.translate(0f, 0.51f, -1.1f);
            }
        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        int i = 1;
        int j = 1;
        float editfactor = 0f;
        for (TileMacdo.burger ingredient : te.getBurgeringredients()) {
            if(!te.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                // make auto return to start of randomtable if it reaches the end
                if (j >= randomTable.length) {
                    j = 0;
                }
            }
            if(ingredient == TileMacdo.burger.BREAD) {
                GlStateManager.translate(0.5f, -0.51f + (editfactor * i), -0.9f);
                GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("bread", (byte) te.getBlockMetadata());
                GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                editfactor = 0.01f;
                GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);

            }
            if(ingredient == TileMacdo.burger.STEAK) {
                GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("Steak", (byte) te.getBlockMetadata());
                GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                editfactor = 0.015f;
                GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);

            }
            if(ingredient == TileMacdo.burger.BACON) {
                GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("bacon", (byte) te.getBlockMetadata());
                GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                editfactor = 0.015f;
                GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);

            }
            if(ingredient == TileMacdo.burger.CHEESE) {
                if(te.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(45, 0, 0, 1);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("cheese", (byte) te.getBlockMetadata());
                    editfactor = 0.01f;
                    GlStateManager.rotate(45, 0, 0, -1);
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                } else {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("cheese", (byte) te.getBlockMetadata());
                    GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                    editfactor = 0.006f;
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                }


            }
            if(ingredient == TileMacdo.burger.SALAD) {
                if(te.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(45, 0, 0, 1);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("salad", (byte) te.getBlockMetadata());
                    editfactor = 0.01f;
                    GlStateManager.rotate(45, 0, 0, -1);
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                } else {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("salad", (byte) te.getBlockMetadata());
                    GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                    editfactor = 0.01f;
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                }
            }
            if(ingredient == TileMacdo.burger.TOMATO) {
                if(te.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(45, 0, 0, 1);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("tomato", (byte) te.getBlockMetadata());
                    editfactor = 0.01f;
                    GlStateManager.rotate(45, 0, 0, -1);
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                } else {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("tomato", (byte) te.getBlockMetadata());
                    GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                    editfactor = 0.01f;
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                }
            }
            if(ingredient == TileMacdo.burger.CHICKEN) {
                if(te.getBurgeringredients().get(0).equals(TileMacdo.burger.BAGUETTE)) {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(45, 0, 0, 1);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("chiken", (byte) te.getBlockMetadata());
                    editfactor = 0.009f;
                    GlStateManager.rotate(45, 0, 0, -1);
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                } else {
                    GlStateManager.translate(0.5f, -0.51f+ (editfactor * i), -0.9f);
                    GlStateManager.rotate(randomTable[j-1], 0, 1, 0);
                    DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("chiken", (byte) te.getBlockMetadata());
                    GlStateManager.rotate(randomTable[j-1], 0, -1, 0);
                    editfactor = 0.01f;
                    GlStateManager.translate(-0.5f, 0.51f+ (editfactor * i), 0.9f);
                }
            }
            if(ingredient == TileMacdo.burger.BAGUETTE) {
                GlStateManager.translate(0.5f, -0.45f+ (editfactor * i), -0.9f);
                DynamXContext.getObjModelRegistry().getModel(te.getBlockObjectInfo().getModel()).renderGroups("baguette", (byte) te.getBlockMetadata());
                editfactor = 0.01f;
                GlStateManager.translate(-0.5f, 0.45f+ (editfactor * i), 0.9f);
            }

            j++;
        }




        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
}
