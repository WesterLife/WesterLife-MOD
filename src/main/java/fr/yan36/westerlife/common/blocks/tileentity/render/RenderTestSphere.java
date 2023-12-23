package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.client.renders.model.renderer.ObjObjectRenderer;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.yan36.westerlife.common.blocks.tileentity.TileCarPresentation;
import fr.yan36.westerlife.common.blocks.tileentity.TileTestSphere;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Map;

public class RenderTestSphere extends TESRDynamXBlock<TileTestSphere> {


    @Override
    public void render(TileTestSphere te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1f + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);

        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderModel((byte) te.getBlockMetadata(), false);

        GL11.glShadeModel(GL11.GL_FLAT);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

        GlStateManager.translate(0, 1.3f, 0);

        EntityRenderer.drawNameplate(Minecraft.getMinecraft().fontRenderer, String.valueOf(GL11.glGetError()), 0, 0, 0, 0, 0, 0, false, false);


        GlStateManager.popMatrix();
    }


}
