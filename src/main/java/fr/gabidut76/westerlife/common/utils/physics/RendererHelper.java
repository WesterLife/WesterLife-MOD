package fr.gabidut76.westerlife.common.utils.physics;

import com.jme3.math.Vector3f;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;


public class RendererHelper {
    public static void drawLine(Vector3f from, Vector3f to, Color color, float lineWidth){
        GlStateManager.glLineWidth(lineWidth);
        GlStateManager.glBegin(GL11.GL_LINES);
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue());
        GlStateManager.glVertex3f(from.x, from.y, from.z);
        GlStateManager.glVertex3f(to.x, to.y, to.z);
        GlStateManager.glEnd();
    }

    public static void drawLine(Vector3f from, Vector3f to, Color color){
        drawLine(from, to, color, 2);
    }

    public static void drawTriangle(Vector3f v0, Vector3f v1, Vector3f v2, Color color){
        drawLine(v0, v1, color);
        drawLine(v1, v2, color);
        drawLine(v2, v0, color);
    }


}
