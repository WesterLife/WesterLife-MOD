package fr.yan36.westerlife.common.blocks.tileentity.render;

import com.kamesuta.mc.signpic.render.RenderHelper;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.blocks.tileentity.TileLyre;
import fr.yan36.westerlife.common.blocks.tileentity.TileScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderScreen extends TESRDynamXBlock<TileScreen> {


    @Override
    public void render(TileScreen te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
//        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("pitch", (byte) te.getBlockMetadata(), false);

//        Minecraft.getMinecraft().renderEngine.bindTexture(te.getPackInfo().get());
        BufferedImage img;
//        try {
//            img = ImageIO.read(new File(te.getUrl()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        RenderHelper.drawRectTexture(GL11.GL_RENDER_MODE);

        GlStateManager.popMatrix();

        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

}
