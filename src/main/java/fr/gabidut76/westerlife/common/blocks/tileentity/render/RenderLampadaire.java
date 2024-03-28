package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.betterlights.BetterLightsMod;
import fr.betterlights.proxy.ClientProxy;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.gabidut76.westerlife.client.utils.ClientUtils;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileBusStop;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileLampadaire;
import fr.gabidut76.westerlife.common.objects.RenderTileBinded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL20.*;

@RenderTileBinded(tileEntityBinded = TileLampadaire.class)
public class RenderLampadaire extends TESRDynamXBlock<TileLampadaire> {


    @Override
    public void render(TileLampadaire te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 0.3f + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("1", (byte) 0, false);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, .5f);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("Cylinder", (byte) 0, false);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();



        // set it very bright

        if(getWorld().getWorldTime() > 13000 && getWorld().getWorldTime() < 23000) {
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



            GlStateManager.color(0.8f, 0.8f, 0.5f, 1f);
            ClientUtils.initBloom(5f);
            DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroup("Cylinder.001", (byte) 0, false);
            ClientUtils.endBloom();

            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);

            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();

            GlStateManager.enableLighting();

            GlStateManager.popMatrix();

        }



        GlStateManager.popMatrix();
    }


}
