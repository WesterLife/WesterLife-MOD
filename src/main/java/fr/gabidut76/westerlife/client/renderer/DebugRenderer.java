package fr.gabidut76.westerlife.client.renderer;

import fr.gabidut76.westerlife.common.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber
public class DebugRenderer {
    public static final Minecraft mc = Minecraft.getMinecraft();
    private static int framebufferTextureId = -1;

    public static DynamicTexture lastPhoneScreenshot;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.DEBUG) {

            GlStateManager.pushMatrix();


            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int screenWidth = scaledResolution.getScaledWidth();
            int screenHeight = scaledResolution.getScaledHeight();

            if (framebufferTextureId == -1) {
                framebufferTextureId = GL11.glGenTextures();
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 36056);
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, mc.displayWidth / 3, 0, mc.displayWidth / 3, mc.displayHeight);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 36056);

            GlStateManager.pushMatrix();
            GlStateManager.scale(1, 0.92, 1);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 1496);
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0.0F, 1.0F);
            GL11.glVertex3f(screenWidth / 1.2075f, screenHeight / 0.695f - screenHeight, 0.0F);
            GL11.glTexCoord2f(0.0F, 0.0F);
            GL11.glVertex3f(screenWidth / 1.2075f, screenHeight / 1.1f, 0.0F);
            GL11.glTexCoord2f(1.0F, 0.0F);
            GL11.glVertex3f(screenWidth / 1.0175f, screenHeight / 1.1f, 0.0F);
            GL11.glTexCoord2f(1.0F, 1.0F);
            GL11.glVertex3f(screenWidth / 1.0175f, screenHeight / 0.695f - screenHeight, 0.0F);
            GL11.glEnd();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 36055);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.translate(screenWidth / 1.2075f, screenHeight / 1.1f, 0);
            Util.drawSplitString(mc.fontRenderer, String.valueOf(36055), 1, 1, 1, 0xFFFFFF);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();




        }
    }


}
