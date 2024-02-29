package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.utils.debug.DynamXDebugOptions;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockAIPoint;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileAIPoint;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.Objects;


public class RenderAIPoint extends TESRDynamXBlock<TileAIPoint> {

    @Override
    public void render(TileAIPoint te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() == DynamXInit.magicWand) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 0.5D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
            GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
            GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            if (te.getType().equals(BlockAIPoint.Type.GO)) {
                int k = te.getPos().getX() % 255;
                GL11.glColor4f(0.0F, k, 0.0F, 1f);
            } else if (te.getType().equals(BlockAIPoint.Type.STOP)) {
                GL11.glColor4f(1.0F, 0.0F, 0.0F, 1f);
            } else if (te.getType().equals(BlockAIPoint.Type.DOMAC_SPAWN)) {
                GL11.glColor4f(0.0F, 0.0F, 1.0F, 1f);
//            EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, "Spawn", 0, 0, 0, 0, 0, 0, false, false);
            } else if (te.getType().equals(BlockAIPoint.Type.DOMAC_TARGET)) {
                GL11.glColor4f(1.0F, 1.0F, 0.0F, 1f);
//            EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, "Target", 0, 0, 0, 0, 0, 0, false, false);
            }

            GlStateManager.translate(0, Math.cos(Minecraft.getMinecraft().world.getTotalWorldTime() / 10.0) / 2, 0);

            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("Cylinder", (byte) te.getBlockMetadata(), false);
            GlStateManager.popMatrix();
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 0.5D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);

        GL11.glLineWidth(5);
        GlStateManager.glBegin(GL11.GL_LINES);

        GlStateManager.glVertex3f(0, 0, 0);

        if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() == DynamXInit.magicWand) {
            if (te.getType().equals(BlockAIPoint.Type.DOMAC_TARGET)) {
                ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
                if (stack.hasTagCompound()) {
                    if (stack.getTagCompound().hasKey("domacSpawnX") && Objects.equals(te.getTarget(), "")) {
                        BlockPos pos2 = Minecraft.getMinecraft().player.getPosition();
                        GlStateManager.glVertex3f((float) pos2.getX() - te.getPos().getX(), (float) pos2.getY() - te.getPos().getY(), (float) pos2.getZ() - te.getPos().getZ());
                    }
                    if (!Objects.equals(te.getTarget(), "")) {
                        BlockPos pos2 = Util.parseBlockPosFromString(te.getTarget());
                        GlStateManager.glVertex3f((float) pos2.getX() - te.getPos().getX(), (float) pos2.getY() - te.getPos().getY(), (float) pos2.getZ() - te.getPos().getZ());
                    }
                }
            } else if (te.getType().equals(BlockAIPoint.Type.DOMAC_SPAWN)) {
                ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
                if (stack.hasTagCompound()) {
                    if (stack.getTagCompound().hasKey("domacSpawnX") && Objects.equals(te.getTarget(), "")) {
                        BlockPos pos2 = Minecraft.getMinecraft().player.getPosition();
                        GlStateManager.glVertex3f((float) pos2.getX() - te.getPos().getX(), (float) pos2.getY() - te.getPos().getY() + 0.5f, (float) pos2.getZ() - te.getPos().getZ());
                    }
                    if (!Objects.equals(te.getTarget(), "")) {
                        BlockPos pos2 = Util.parseBlockPosFromString(te.getTarget());
                        GlStateManager.glVertex3f((float) pos2.getX() - te.getPos().getX(), (float) pos2.getY() - te.getPos().getY() + 0.5f, (float) pos2.getZ() - te.getPos().getZ());
                    }
                }
            }
        }


        GlStateManager.glEnd();

        if(DynamXDebugOptions.DEBUG_RENDER.isActive()) {
            if (te.getType().equals(BlockAIPoint.Type.DOMAC_SPAWN)) {
                EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, "Spawn", 0, 1f, 0, 0, 0, 0, false, false);
            } else if (te.getType().equals(BlockAIPoint.Type.DOMAC_TARGET)) {
                EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, "Target", 0, 1f, 0, 0, 0, 0, false, false);
            }
        }



        GlStateManager.popMatrix();


    }
}
