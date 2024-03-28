package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.client.utils.ClientUtils;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileBusStop;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileCokeTable;
import fr.gabidut76.westerlife.common.objects.RenderTileBinded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static de.javagl.jgltf.model.GltfConstants.GL_EQUAL;
import static fr.aym.acsguis.component.panel.GuiFrame.resolution;
import static org.lwjgl.opengl.GL11.*;

@RenderTileBinded(tileEntityBinded = TileBusStop.class)
public class RenderBusStop extends TESRDynamXBlock<TileBusStop> {


    @Override
    public void render(TileBusStop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("1", (byte) 0, false);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("3", (byte) 0, false);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("2", (byte) 0, false);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("5", (byte) 0, false);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("6", (byte) 0, false);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, .5f);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("4", (byte) 0, false);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.rotate(-90,0,1,0);
        GlStateManager.translate(-.03f, 2.37f, .56f);

        GlStateManager.scale(0.01, -0.01, 0.01);
        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, te.getStopname(), 0, 0, 60, Color.WHITE.getRGB());

        GlStateManager.popMatrix();

        GlStateManager.disableLighting();


        // set it very bright


        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();

        int j = 61680;
        int k = 0;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        ClientUtils.initBloom(5f);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("lights", (byte) 0, false);
        ClientUtils.endBloom();
        GlStateManager.translate(-.02f, 2.07f, -1.93f);


        GlStateManager.rotate(15, 1, 0, 0);
        GlStateManager.scale(0.01, -0.01, 0.01);


        // CHAT GPT

        String text = te.getStopname();

        float factor = Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 100f;

        GlStateManager.scale(factor, factor, factor);

//        Minecraft.getMinecraft().fontRenderer.dra(text, 0,0, Color.YELLOW.getRGB());
        ClientUtils.initBloom(2f);
        Util.drawSplitString2(Minecraft.getMinecraft().fontRenderer, text, 0, 0, 60, Color.YELLOW.getRGB());
        ClientUtils.endBloom();
        //


        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();

        GlStateManager.enableLighting();

        GlStateManager.popMatrix();


        GlStateManager.popMatrix();
    }


}
